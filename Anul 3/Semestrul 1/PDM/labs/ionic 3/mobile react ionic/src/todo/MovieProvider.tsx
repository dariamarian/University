import React, {useCallback, useContext, useEffect, useReducer, useRef, useState} from 'react';
import PropTypes from 'prop-types';
import {getLogger} from '../core';
import {MovieProps} from './MovieProps';

import {createMovie, getMovies, newWebSocket,  updateMovie} from './MovieApi';
import {AuthContext} from "../auth/AuthProvider";
import {useNetwork} from "./useNetwork";
import { Preferences } from '@capacitor/preferences';

const log = getLogger('MovieProvider');

type SaveMovieFn = (movie: MovieProps) => Promise<any>;

export interface MoviesState {
  movies?: MovieProps[],
  fetching: boolean,
  fetchingError?: Error | null,
  saving: boolean,
  savingError?: Error | null,
  saveMovie?: SaveMovieFn,
  page :number,
  pageSize:number,
  setPage: React.Dispatch<React.SetStateAction<number>>,
  setPageSize: React.Dispatch<React.SetStateAction<number>>,
}

interface ActionProps {
  type: string,
  payload?: any,
}

const initialState: MoviesState = {
  fetching: false,
  saving: false,
  page: 0,
  pageSize: 5,
  setPage: () => {},
  setPageSize: () => {}
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
        const index = movies.findIndex(it => it.title === movie.title);
        if (index === -1) {
          movies.splice(0, 0, movie);
        } else {
          movies[index] = movie;
        }
        return { ...state,  movies: movies, saving: false };
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
  const [page, setPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(5);
  const [state, dispatch] = useReducer(reducer, {...initialState,
    ...initialState,
    page,
    pageSize,
    setPage,
    setPageSize,
  });
  const { networkStatus} = useNetwork();
  const { movies, fetching, fetchingError, saving, savingError } = state;
  const {token} = useContext(AuthContext);
  const isOnline = networkStatus.connected;

  useEffect(getMoviesEffect, [token, page,pageSize,isOnline]);
  useEffect(wsEffect, [token])
  useEffect(() => {
    if (isOnline) {
      const synchronizeMovies = async () => {
        try {
          const existingMovies = await getMoviesFromPreferences();
          for (const movie of existingMovies) {

            await (movie.title ? createMovie(token, movie) : updateMovie(movie));
          }

          await Preferences.remove({ key: 'movies' });
        } catch (error) {
          log('Error synchronizing movies:', error);
        }
      };


      synchronizeMovies();
    }
    async function getMoviesFromPreferences(): Promise<MovieProps[]> {
      try {

        const moviesJSON = await Preferences.get({key: 'movies'});
        console.log(moviesJSON);
        const movies: MovieProps[] = moviesJSON.value ? JSON.parse(moviesJSON.value) : [];

        log('Movies retrieved from preferences:', movies);
        return movies;
      } catch (error) {
        log('Error retrieving movies from preferences:', error);
        return [];
      }
    }

  }, [isOnline, token]);



  const saveMovie = useCallback<SaveMovieFn>(saveMovieCallback, [token,isOnline]);
  const value = {movies, fetching, fetchingError, saving, savingError, saveMovie ,page,pageSize,setPage,setPageSize};
  log('returns');
  return (
    <MovieContext.Provider value={value}>
      {children}
    </MovieContext.Provider>
  );



  function getMoviesEffect() {
    let canceled = false;

    console.log(isOnline);

    fetchMovies(page, pageSize);

    return () => {
      canceled = true;
    }

    async function fetchMovies(page: number, pageSize: number) {
      if (token && isOnline) {
        try {
          log('fetchMovies started');
          dispatch({type: FETCH_MOVIES_STARTED});
          log("token " + token);
          const movies = await getMovies(token, page, pageSize);
          log('fetchMovies succeeded');
          if (!canceled) {
            dispatch({type: FETCH_MOVIES_SUCCEEDED, payload: {movies: movies}});
          }
        } catch (error) {
          log('fetchMovies failed');
          if (!canceled) {
            dispatch({type: FETCH_MOVIES_FAILED, payload: {error}});
          }
        }
      }else{
        const newMovies =await getMoviesFromPreferences();
        console.log("ssss" + newMovies);
        dispatch({type: FETCH_MOVIES_SUCCEEDED, payload: {movies: newMovies || [] }});
      }
    }

    async function getMoviesFromPreferences(): Promise<MovieProps[]> {
      try {

        const moviesJSON = await Preferences.get({key: 'movies'});
        console.log(moviesJSON);
        const movies: MovieProps[] = moviesJSON.value ? JSON.parse(moviesJSON.value) : [];

        log('Movies retrieved from preferences:', movies);
        return movies;
      } catch (error) {
        log('Error retrieving movies from preferences:', error);
        return [];
      }
    }

  }
    async function saveMovieCallback(movie: MovieProps) {
      try {
        log('saveMovie started');
        dispatch({type: SAVE_MOVIE_STARTED});
        log(isOnline);
        if (isOnline) {
          const savedMovie = await (movie.title ? createMovie(token, movie) : updateMovie(movie));
          log('saveMovie succeeded');
          dispatch({type: SAVE_MOVIE_SUCCEEDED, payload: {movie: savedMovie}});
        } else {
          const existingMovies = await getMoviesFromPreferences();

          existingMovies.push(movie);

          const updatedMoviesJSON = JSON.stringify(existingMovies);

          await Preferences.set({
            key: 'movies',
            value: updatedMoviesJSON,
          });
          dispatch({type: SAVE_MOVIE_SUCCEEDED, payload: {movie: movie}});
        }
      } catch (error) {
        log('saveMovie failed');
        dispatch({type: SAVE_MOVIE_FAILED, payload: {error}});
      }

      async function getMoviesFromPreferences(): Promise<MovieProps[]> {
        try {

          const moviesJSON = await Preferences.get({key: 'movies'});

          const movies: MovieProps[] = moviesJSON.value ? JSON.parse(moviesJSON.value) : [];

          log('Movies retrieved from preferences:', movies);
          return movies;
        } catch (error) {
          log('Error retrieving movies from preferences:', error);
          return [];
        }
      }

    }

  function wsEffect() {

    let canceled = false;
    log('wsEffect - connecting');
    let closeWebSocket: () => void;
    if (token?.trim()) {
      log("inainte de conectare");
      closeWebSocket = newWebSocket(token, message => {
        if (canceled) {
          return;
        }
        const movie = message;
        log(movie.title);

        dispatch({type: SAVE_MOVIE_SUCCEEDED, payload: {movie}});

      });
    }
    return () => {
      log('wsEffect - disconnecting');
      canceled = true;
      closeWebSocket?.();
    }
  }
};
