const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({ server });
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());
app.use(async (ctx, next) => {
  const start = new Date();
  await next();
  const ms = new Date() - start;
  console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

app.use(async (ctx, next) => {
  await new Promise(resolve => setTimeout(resolve, 2000));
  await next();
});

app.use(async (ctx, next) => {
  try {
    await next();
  } catch (err) {
    ctx.response.body = { issue: [{ error: err.message || 'Unexpected error' }] };
    ctx.response.status = 500;
  }
});

class Movie {
  constructor({ id, title, releaseDate, rating, watched, version, date }) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.rating = rating;
    this.watched = watched;
    this.version = version;
    this.date = date; // Keep the date field
  }
}

const movies = [];
let lastId = 0; // Start with ID 0
const pageSize = 10;

const broadcast = data =>
    wss.clients.forEach(client => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(JSON.stringify(data));
      }
    });

const router = new Router();

router.get('/movie', ctx => {
  ctx.response.body = movies;
  ctx.response.status = 200;
});

router.get('/movie/:id', async (ctx) => {
  const movieId = ctx.request.params.id;
  const movie = movies.find(movie => movieId === movie.id);
  if (movie) {
    ctx.response.body = movie;
    ctx.response.status = 200; // ok
  } else {
    ctx.response.body = { message: `movie with id ${movieId} not found` };
    ctx.response.status = 404; // NOT FOUND
  }
});

const createMovie = async (ctx) => {
  const movie = ctx.request.body;
  if (!movie.title) { // validation
    ctx.response.body = { message: 'Title is missing' };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  if (!movie.releaseDate) { // validation
    ctx.response.body = { message: 'Release Date is missing' };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  if (!movie.rating) { // validation
    ctx.response.body = { message: 'Rating is missing' };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  if (isNaN(movie.rating) || movie.rating < 1 || movie.rating > 10) {
    ctx.response.body = { message: 'Rating should be a number between 1 and 10' };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  if (!movie.watched) { // validation
    ctx.response.body = { message: 'Watched is missing' };
    ctx.response.status = 400; //  BAD REQUEST
    return;
  }
  movie.id = `${lastId++}`; // Increment the ID and set it
  movie.date = new Date();
  movie.version = 1;
  movies.push(movie);
  ctx.response.body = movie;
  ctx.response.status = 201; // CREATED
  broadcast({ event: 'created', payload: { movie } });
};

router.post('/movie', async (ctx) => {
  await createMovie(ctx);
});

router.put('/movie/:id', async (ctx) => {
  const id = ctx.params.id;
  const movie = ctx.request.body;
  movie.date = new Date();
  const movieId = movie.id;
  if (movieId && id !== movie.id) {
    ctx.response.body = { message: `Param id and body id should be the same` };
    ctx.response.status = 400; // BAD REQUEST
    return;
  }
  if (!movieId) {
    await createMovie(ctx);
    return;
  }
  const index = movies.findIndex(movie => movie.id === id);
  if (index === -1) {
    ctx.response.body = { issue: [{ error: `movie with id ${id} not found` }] };
    ctx.response.status = 400; // BAD REQUEST
    return;
  }
  const movieVersion = parseInt(ctx.request.get('ETag')) || movie.version;
  if (movieVersion < movies[index].version) {
    ctx.response.body = { issue: [{ error: `Version conflict` }] };
    ctx.response.status = 409; // CONFLICT
    return;
  }
  movie.version++;
  movies[index] = movie;
  lastUpdated = new Date();
  ctx.response.body = movie;
  ctx.response.status = 200; // OK
  broadcast({ event: 'updated', payload: { movie } });
});

router.del('/movie/:id', ctx => {
  const id = ctx.params.id;
  const index = movies.findIndex(movie => id === movie.id);
  if (index !== -1) {
    const movie = movies[index];
    movies.splice(index, 1);
    lastUpdated = new Date();
    broadcast({ event: 'deleted', payload: { movie } });
  }
  ctx.response.status = 204; // no content
});

setInterval(() => {
  lastUpdated = new Date();
  const movie = new Movie({ id: `${lastId}`, title: `movie ${lastId}`, releaseDate: new Date(), rating: 1, watched: false, version: 1, date: lastUpdated });
  movies.push(movie);
  console.log(`New movie: ${movie.title}, ${movie.releaseDate}`);
  broadcast({ event: 'created', payload: { movie } });
  lastId++; // Increment the lastId
}, 5000);

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
