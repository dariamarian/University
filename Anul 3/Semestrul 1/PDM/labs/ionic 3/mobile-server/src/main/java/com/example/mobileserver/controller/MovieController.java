package com.example.mobileserver.controller;

import com.example.mobileserver.model.Movie;
import com.example.mobileserver.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<List<Movie>> getMovies(Authentication auth, Pageable pageable) {

    return new ResponseEntity<>(movieService.getMovies(auth.getName(),pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Movie> createMovie(@RequestBody Movie movie, Authentication auth){
    System.out.println("add " + auth.getName());
    return new ResponseEntity<>(movieService.createMovie(movie, auth.getName()), HttpStatus.CREATED);
  }

}
