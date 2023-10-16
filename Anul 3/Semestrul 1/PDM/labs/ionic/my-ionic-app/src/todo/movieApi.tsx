import axios from 'axios';
import { getLogger } from '../core';
import { MovieProps } from './MovieProps';

//const log = getLogger('movieApi');

const baseUrl = 'localhost:3000';
const movieUrl = `http://${baseUrl}/movie`;

interface ResponseProps<T> {
  data: T;
}

function withLogs<T>(promise: Promise<ResponseProps<T>>, fnName: string): Promise<T> {
  log(`${fnName} - started`);
  return promise
      .then((res) => {
        log(`${fnName} - succeeded`);
        return Promise.resolve(res.data);
      })
      .catch((err) => {
        log(`${fnName} - failed`);
        return Promise.reject(err);
      });
}

const config = {
  headers: {
    'Content-Type': 'application/json',
  },
};

export const getMovies: () => Promise<MovieProps[]> = () => {
  return withLogs(axios.get(movieUrl, config), 'getMovies');
};

export const createMovie: (movie: MovieProps) => Promise<MovieProps> = (movie) => {
  return withLogs(axios.post(movieUrl, movie, config), 'createMovie');
};

export const updateMovie: (movie: MovieProps) => Promise<MovieProps> = (movie) => {
  return withLogs(axios.put(`${movieUrl}/${movie.id}`, movie, config), 'updateMovie');
};

interface MessageData {
  event: string;
  payload: {
    movie: MovieProps;
  };
}

const log = getLogger('ws');

export const newWebSocket = (onMessage: (data: MessageData) => void) => {
  const ws = new WebSocket(`ws://${baseUrl}`);
  ws.onopen = () => {
    log('web socket onopen');
  };
  ws.onclose = () => {
    log('web socket onclose');
  };
  ws.onerror = (error) => {
    log('web socket onerror', error);
  };
  ws.onmessage = (messageEvent) => {
    log('web socket onmessage');
    onMessage(JSON.parse(messageEvent.data));
  };
  return () => {
    ws.close();
  };
};