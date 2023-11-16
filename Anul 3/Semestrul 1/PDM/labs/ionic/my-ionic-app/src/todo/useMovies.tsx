import { useCallback, useEffect, useReducer } from 'react';
import { getLogger } from '../core';
import { MovieProps } from './MovieProps';
import { getMovies } from './movieApi';

const log = getLogger('useMovies');

export interface MoviesState {
  movies?: MovieProps[],
  fetching: boolean,
  fetchingError?: Error,
}

export interface MoviesProps extends MoviesState {
  addMovie: () => void,
}

interface ActionProps {
  type: string,
  payload?: any,
}

const initialState: MoviesState = {
  movies: undefined,
  fetching: false,
  fetchingError: undefined,
};

const FETCH_MOVIES_STARTED = 'FETCH_MOVIES_STARTED';
const FETCH_MOVIES_SUCCEEDED = 'FETCH_MOVIES_SUCCEEDED';
const FETCH_MOVIES_FAILED = 'FETCH_MOVIES_FAILED';

const reducer: (state: MoviesState, action: ActionProps) => MoviesState =
    (state, { type, payload }) => {
      switch(type) {
        case FETCH_MOVIES_STARTED:
          return { ...state, fetching: true };
        case FETCH_MOVIES_SUCCEEDED:
          return { ...state, movies: payload.movies, fetching: false };
        case FETCH_MOVIES_FAILED:
          return { ...state, fetchingError: payload.error, fetching: false };
        default:
          return state;
      }
    };

export const useMovies: (token:string) => MoviesProps = (token) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { movies, fetching, fetchingError } = state;
  const addMovie = useCallback(() => {
    log('addMovie - TODO');
  }, []);
  useEffect(() => getMoviesEffect(token), [dispatch, token]);
  log(`returns - fetching = ${fetching}, movies = ${JSON.stringify(movies)}`);
  return {
    movies,
    fetching,
    fetchingError,
    addMovie,
  };

  function getMoviesEffect(token: string) {
    let canceled = false;
    fetchMovies();
    return () => {
      canceled = true;
    }

    async function fetchMovies() {
      try {
        log('fetchMovies started');
        dispatch({ type: FETCH_MOVIES_STARTED });
        const movies = await getMovies(token);
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
};
