import React, { useCallback, useEffect, useReducer } from 'react';
import PropTypes from 'prop-types';
import { getLogger } from '../core';
import { MovieProps } from './MovieProps';
import { createMovie, getMovies, newWebSocket, updateMovie } from './movieApi';

const log = getLogger('MovieProvider');

type SaveMovieFn = (movie: MovieProps) => Promise<any>;

export interface MoviesState {
  movies?: MovieProps[],
  fetching: boolean,
  fetchingError?: Error | null,
  saving: boolean,
  savingError?: Error | null,
  saveMovie?: SaveMovieFn,
}

interface ActionProps {
  type: string,
  payload?: any,
}

const initialState: MoviesState = {
  fetching: false,
  saving: false,
};

const FETCH_MOVIES_STARTED = 'FETCH_MOVIES_STARTED';
const FETCH_MOVIES_SUCCEEDED = 'FETCH_MOVIES_SUCCEEDED';
const FETCH_MOVIES_FAILED = 'FETCH_MOVIES_FAILED';
const SAVE_MOVIE_STARTED = 'SAVE_MOVIE_STARTED';
const SAVE_MOVIE_SUCCEEDED = 'SAVE_MOVIE_SUCCEEDED';
const SAVE_MOVIE_FAILED = 'SAVE_MOVIE_FAILED';

const reducer: (state: MoviesState, action: ActionProps) => MoviesState =
    (state, { type, payload }) => {
      switch(type) {
        case FETCH_MOVIES_STARTED:
          return { ...state, fetching: true, fetchingError: null };
        case FETCH_MOVIES_SUCCEEDED:
          return { ...state, movies: payload.movies, fetching: false };
        case FETCH_MOVIES_FAILED:
          return { ...state, fetchingError: payload.error, fetching: false };
        case SAVE_MOVIE_STARTED:
          return { ...state, savingError: null, saving: true };
        case SAVE_MOVIE_SUCCEEDED:
          const movies = [...(state.movies || [])];
          const movie = payload.movie;
          const index = movies.findIndex(m => m.id === movie.id);
          if (index === -1) {
            movies.splice(0, 0, movie);
          } else {
            movies[index] = movie;
          }
          return { ...state,  movies, saving: false };
        case SAVE_MOVIE_FAILED:
          return { ...state, savingError: payload.error, saving: false };
        default:
          return state;
      }
    };

export const MovieContext = React.createContext<MoviesState>(initialState);

interface MovieProviderProps {
  children: PropTypes.ReactNodeLike,
}

export const MovieProvider: React.FC<MovieProviderProps> = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { movies, fetching, fetchingError, saving, savingError } = state;
  useEffect(getMoviesEffect, []);
  useEffect(wsEffect, []);
  const saveMovie = useCallback<SaveMovieFn>(saveMovieCallback, []);
  const value = { movies, fetching, fetchingError, saving, savingError, saveMovie };
  log('returns');
  return (
      <MovieContext.Provider value={value}>
        {children}
      </MovieContext.Provider>
  );

  function getMoviesEffect() {
    let canceled = false;
    fetchMovies();
    return () => {
      canceled = true;
    }

    async function fetchMovies() {
      try {
        log('fetchMovies started');
        dispatch({ type: FETCH_MOVIES_STARTED });
        const movies = await getMovies();
        log('fetchMovies succeeded');
        if (!canceled) {
          dispatch({ type: FETCH_MOVIES_SUCCEEDED, payload: { movies } });
        }
      } catch (error) {
        log('fetchMovies failed');
        if (!canceled) {
          dispatch({ type: FETCH_MOVIES_FAILED, payload: { error } });
        }
      }
    }
  }

  async function saveMovieCallback(movie: MovieProps) {
    try {
      log('saveMovie started');
      dispatch({ type: SAVE_MOVIE_STARTED });
      const savedMovie = await (movie.id ? updateMovie(movie) : createMovie(movie));
      log('saveMovie succeeded');
      dispatch({ type: SAVE_MOVIE_SUCCEEDED, payload: { movie: savedMovie } });
    } catch (error) {
      log('saveMovie failed');
      dispatch({ type: SAVE_MOVIE_FAILED, payload: { error } });
    }
  }

  function wsEffect() {
    let canceled = false;
    log('wsEffect - connecting');
    const closeWebSocket = newWebSocket(message => {
      if (canceled) {
        return;
      }
      const { event, payload: { movie }} = message;
      log(`ws message, movie ${event}`);
      if (event === 'created' || event === 'updated') {
        dispatch({ type: SAVE_MOVIE_SUCCEEDED, payload: { movie } });
      }
    });
    return () => {
      log('wsEffect - disconnecting');
      canceled = true;
      closeWebSocket();
    }
  }
};
