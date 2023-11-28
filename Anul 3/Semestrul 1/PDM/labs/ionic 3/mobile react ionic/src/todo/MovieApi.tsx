import axios from 'axios';
import {authConfig, getLogger} from '../core';
import {MovieProps} from './MovieProps';
import {Client} from '@stomp/stompjs';

const log = getLogger('MovieApi');

const baseUrl = 'localhost:8080';
const movieUrl = `http://${baseUrl}/api/movies`;
const wsUrl = 'ws://localhost:8080/movie-notifications';

interface ResponseProps<T> {
  data: T;
}

function withLogs<T>(promise: Promise<ResponseProps<T>>, fnName: string): Promise<T> {
  log(`${fnName} - started`);
  return promise
    .then(res => {
      log(`${fnName} - succeeded`);
      return Promise.resolve(res.data);
    })
    .catch(err => {
      log(`${fnName} - failed`);
      return Promise.reject(err);
    });
}

const config = {
  headers: {
    'Content-Type': 'application/json'
  }
};

export const getMovies: (token:string, page:number, pageSize:number) => Promise<MovieProps[]> = (token, page, pageSize) => {
  return withLogs(axios.get(`${movieUrl}?page=${page}&size=${pageSize}`, authConfig(token)), 'getMovies');
}


export const createMovie: (token :string, movieProps: MovieProps) => Promise<MovieProps[]> = (token , movie) => {
  console.log(movie.title);
  return withLogs(axios.post(movieUrl, movie, authConfig(token)), 'createMovie');
}

export const updateMovie: (movie: MovieProps) => Promise<MovieProps[]> = movie => {
  return withLogs(axios.put(`${movieUrl}/${movie.title}`, movie, config), 'updateMovie');
}

interface MessageData {
  event: string;
  payload: {
    movie: MovieProps;
  };
}

export const newWebSocket = (token :string, onMessage: (movie: MovieProps) => void) => {

  const client = new Client({
    brokerURL: wsUrl,
    connectHeaders: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    },
    onConnect: () => {
      log('connected');
      client.subscribe('/topic/'+localStorage.getItem("username"), message => {
        log('web socket message');
        onMessage(JSON.parse(message.body));
      })
    },
    onStompError: () => {
      log('stomp error');
    },
    onWebSocketError: () => {
      log('web socket error');
    }
  });

  client.activate();

  return () => {
    client.deactivate();
  }
}
