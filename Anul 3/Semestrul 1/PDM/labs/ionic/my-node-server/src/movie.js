import Router from 'koa-router';
import dataStore from 'nedb-promise';
import { broadcast } from './wss.js';

export class MovieStore {
  constructor({ filename, autoload }) {
    this.store = dataStore({ filename, autoload });
  }

  async find(props) {
    return this.store.find(props);
  }

  async findOne(props) {
    return this.store.findOne(props);
  }

  async insert(movie) {
    if (!movie.text) { // validation
      throw new Error('Missing text property')
    }
    return this.store.insert(movie);
  };

  async update(props, movie) {
    return this.store.update(props, movie);
  }

  async remove(props) {
    return this.store.remove(props);
  }
}

const movieStore = new MovieStore({ filename: './db/movies.json', autoload: true });

export const movieRouter = new Router();

movieRouter.get('/', async (ctx) => {
  const userId = ctx.state.user._id;
  ctx.response.body = await movieStore.find({ userId });
  ctx.response.status = 200; // ok
});

movieRouter.get('/:id', async (ctx) => {
  const userId = ctx.state.user._id;
  const movie = await noteStore.findOne({ _id: ctx.params.id });
  const response = ctx.response;
  if (movie) {
    if (movie.userId === userId) {
      ctx.response.body = movie;
      ctx.response.status = 200; // ok
    } else {
      ctx.response.status = 403; // forbidden
    }
  } else {
    ctx.response.status = 404; // not found
  }
});

const createMovie = async (ctx, movie, response) => {
  try {
    const userId = ctx.state.user._id;
    movie.userId = userId;
    response.body = await movieStore.insert(movie);
    response.status = 201; // created
    broadcast(userId, { type: 'created', payload: movie });
  } catch (err) {
    response.body = { message: err.message };
    response.status = 400; // bad request
  }
};

movieRouter.post('/', async ctx => await createMovie(ctx, ctx.request.body, ctx.response));

movieRouter.put('/:id', async ctx => {
  const movie = ctx.request.body;
  const id = ctx.params.id;
  const movieId = movie._id;
  const response = ctx.response;
  if (movieId && movieId !== id) {
    response.body = { message: 'Param id and body _id should be the same' };
    response.status = 400; // bad request
    return;
  }
  if (!movieId) {
    await createMovie(ctx, movie, response);
  } else {
    const userId = ctx.state.user._id;
    movie.userId = userId;
    const updatedCount = await movieStore.update({ _id: id }, movie);
    if (updatedCount === 1) {
      response.body = movie;
      response.status = 200; // ok
      broadcast(userId, { type: 'updated', payload: movie });
    } else {
      response.body = { message: 'Resource no longer exists' };
      response.status = 405; // method not allowed
    }
  }
});

movieRouter.del('/:id', async (ctx) => {
  const userId = ctx.state.user._id;
  const movie = await movieStore.findOne({ _id: ctx.params.id });
  if (movie && userId !== movie.userId) {
    ctx.response.status = 403; // forbidden
  } else {
    await movieStore.remove({ _id: ctx.params.id });
    ctx.response.status = 204; // no content
  }
});
