import Router from "koa-router";
import { movieStore } from "./store";
import { broadcast } from "../utils";

export const router = new Router();

router.get("/", async (ctx) => {
  console.log("hereeee")
  const response = ctx.response;
  const userId = ctx.state.user._id;
  response.body = await movieStore.find({ userId });
  response.status = 200; // ok
});

router.get("/:id", async (ctx) => {
  const userId = ctx.state.user._id;
  const movie = await movieStore.findOne({ _id: ctx.params.id });
  const response = ctx.response;
  if (movie) {
    if (movie.userId === userId) {
      response.body = movieItem;
      response.status = 200; // ok
    } else {
      response.status = 403; // forbidden
    }
  } else {
    response.status = 404; // not found
  }
});



const createMovie = async (ctx, movie, response) => {
  try{
      const userId=ctx.state.user._id;
      movie.userId=userId;
      movie._id=undefined
      const insertedMovie = await movieStore.insert(movie);
      response.body = { ...insertedMovie, _id: insertedMovie._id };
      console.log('response.body',response.body)
      response.status = 201; // CREATED
      broadcast(userId,{type: 'created', payload: insertedMovie});
  }catch(err){
      response.body={message:err.message};
      response.status=400;
  }
};

router.put('/sync', async (ctx) => {
  try{
      const userId = ctx.state.user._id;
      console.log("userId: "+userId)
      const movies = ctx.request.body;
      console.log("movies="+movies)
      const response = ctx.response;
      const currentMovies = await movieStore.find({ userId });
      for (var movie of movies) {
        movie.userId=userId
          if (!movie._id)
          {
            await createMovie(ctx,movie,ctx.response)
          }
          else{
              const existingMovie = currentMovies.find(m => m._id === movie._id);
              if (existingMovie) {
                  await movieStore.update({ _id: movie._id }, movie);
              }
          }
      }

      response.body = await movieStore.find({ userId });
      response.status = 200;
      //broadcast(userId, { type: 'sync', payload: response.body });
  }
  catch (err) {
      console.log(err)
      ctx.response.body = { message: err.message };
      ctx.response.status = 400;
  }

});
router.post('/', async (ctx) => {
  console.log('body', ctx.request.body);
  await createMovie(ctx, ctx.request.body, ctx.response);
});

router.put("/:id", async (ctx) => {
  const movieItem = ctx.request.body;
  const id = ctx.params.id;
  const movieItemId = movieItem._id;
  const response = ctx.response;
  if (movieItemId && movieItemId !== id) {
    response.body = { message: "Param id and body _id should be the same" };
    response.status = 400; // bad request
    return;
  }
  if (!movieItemId) {
    await createMovie(ctx, movieItem, response);
  } else {
    const userId = ctx.state.user._id;
    movieItem.userId = userId;
    const updatedCount = await movieStore.update(
      { _id: id },
      movieItem
    );
    if (updatedCount === 1) {
      response.body = movieItem;
      response.status = 200; // ok
      broadcast(userId, { type: "updated", payload: movieItem });
    } else {
      response.body = { message: "Resource no longer exists" };
      response.status = 405; // method not allowed
    }
  }
});

router.del("/:id", async (ctx) => {
  const userId = ctx.state.user._id;
  const movieItem = await movieStore.findOne({ _id: ctx.params.id });
  if (movieItem && userId !== movieItem.userId) {
    ctx.response.status = 403; // forbidden
  } else {
    await movieStore.remove({ _id: ctx.params.id });
    ctx.response.status = 204; // no content
  }
});


