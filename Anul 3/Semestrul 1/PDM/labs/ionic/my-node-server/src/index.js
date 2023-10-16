const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

const wss = new WebSocket.Server({ server });

app.use(bodyparser());
app.use(cors());


// Create a router for movie-related routes
const router = new Router();

const movies = [];
let lastId = 0; // Start with ID 0
let lastUpdated = new Date();

// WebSocket broadcast function
const broadcast = (data) => {
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });
};

class Movie {
  constructor({ id, title, releaseDate, rating, watched, version }) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.rating = rating;
    this.watched = watched;
    this.version = version;
  }
}

router.get('/movie', (ctx) => {
  ctx.response.body = movies;
  ctx.response.status = 200;
});

router.get('/movie/:id', async (ctx) => {
  const movieId = ctx.params.id;
  const movie = movies.find((movie) => movieId === movie.id);
  if (movie) {
    ctx.response.body = movie;
    ctx.response.status = 200;
  } else {
    ctx.response.body = { message: `movie with id ${movieId} not found` };
    ctx.response.status = 404;
  }
});

const createMovie = async (ctx) => {
  const movie = ctx.request.body;
  if (!movie.title || !movie.releaseDate || !movie.rating || isNaN(movie.rating) || movie.rating < 1 || movie.rating > 10 || movie.watched === undefined) {
    ctx.response.body = { message: 'Invalid movie data' };
    ctx.response.status = 400;
    return;
  }
  movie.id = `${lastId++}`;
  movie.date = new Date();
  movie.version = 1;
  movies.push(movie);
  ctx.response.body = movie;
  ctx.response.status = 201;
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
    ctx.response.status = 400;
    return;
  }
  if (!movieId) {
    await createMovie(ctx);
    return;
  }
  const index = movies.findIndex((movie) => movie.id === id);
  if (index === -1) {
    ctx.response.body = { issue: [{ error: `movie with id ${id} not found` }] };
    ctx.response.status = 400;
    return;
  }
  const movieVersion = parseInt(ctx.request.get('ETag')) || movie.version;
  if (movieVersion < movies[index].version) {
    ctx.response.body = { issue: [{ error: `Version conflict` }] };
    ctx.response.status = 409;
    return;
  }
  movie.version++;
  movies[index] = movie;
  lastUpdated = new Date();
  ctx.response.body = movie;
  ctx.response.status = 200;
  broadcast({ event: 'updated', payload: { movie } });
});

router.del('/movie/:id', (ctx) => {
  const id = ctx.params.id;
  const index = movies.findIndex((movie) => id === movie.id);
  if (index !== -1) {
    const movie = movies[index];
    movies.splice(index, 1);
    lastUpdated = new Date();
    broadcast({ event: 'deleted', payload: { movie } });
  }
  ctx.response.status = 204;
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000, () => {
  console.log('Server started on port 3000');
});

// WebSocket event handling
wss.on('connection', (ws) => {
  console.log('WebSocket client connected');

  ws.on('message', (message) => {
    console.log(`Received: ${message}`);
  });

  ws.on('close', () => {
    console.log('WebSocket client disconnected');
  });
});
