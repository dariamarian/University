package com.example.mobileserver.service;

import com.example.mobileserver.model.Movie;
import com.example.mobileserver.repository.MovieRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  private final SimpMessagingTemplate simpMessagingTemplate;

  public MovieService(SimpMessagingTemplate simpMessagingTemplate, MovieRepository movieRepository) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.movieRepository = movieRepository;
  }


  public List<Movie> getMovies(String username, Pageable pageable) {
    return movieRepository.findByUsername(username, pageable).getContent();

  }
  public Movie createMovie(Movie movie, String username) {
    System.out.println(movie.getTitle());
    System.out.println(movie.getDate());
    movie.setUsername(username);
    movieRepository.save(movie);
    sendNotification(movie,username);
    return movie;
  }

  public void sendNotification(Movie movie, String username) {

    simpMessagingTemplate.convertAndSend(String.format("/topic/%s",username), movie);
  }


}
