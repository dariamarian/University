import axios from 'axios';
import { authConfig, baseUrl, getLogger, withLogs } from '../core';
import { MovieProps } from './MovieProps';

const movieUrl = `http://${baseUrl}/movie`;

export const getMovies: (token: string) => Promise<MovieProps[]> = token => {
  return withLogs(axios.get(movieUrl, authConfig(token))
      .then(response => response.data)  // Extract the data from the response
      .catch(error => {
        // Handle error, log it, or throw a custom error
        console.error("Error while fetching movies:", error);
        throw error;  // You can also return a custom error message here
      }), 'getProps');
}


export const createMovie: (token: string, movie: MovieProps) => Promise<MovieProps[]> = (token, movie) => {
  return withLogs(axios.post(movieUrl, movie, authConfig(token)), 'createMovie');
}

export const updateMovie: (token: string, movie: MovieProps) => Promise<MovieProps[]> = (token, movie) => {
  return withLogs(axios.put(`${movieUrl}/${movie.id}`, movie, authConfig(token)), 'updateMovie');
}

interface MessageData {
  type: string;
  payload: MovieProps;
}

const log = getLogger('ws');

export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
  const ws = new WebSocket(`ws://${baseUrl}`);
  ws.onopen = () => {
    log('web socket onopen');
    ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
  };
  ws.onclose = () => {
    log('web socket onclose');
  };
  ws.onerror = error => {
    log('web socket onerror', error);
  };
  ws.onmessage = messageEvent => {
    log('web socket onmessage');
    onMessage(JSON.parse(messageEvent.data));
  };
  return () => {
    ws.close();
  }
}
