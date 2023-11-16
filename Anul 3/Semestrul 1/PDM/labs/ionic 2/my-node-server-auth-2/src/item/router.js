import Router from 'koa-router';
import itemStore from './store';
import { broadcast } from "../utils";

export const router = new Router();

router.get('/movies', async (ctx) => {
    const response = ctx.response;
    const userId = ctx.state.user._id;
    response.body = await itemStore.find({ userId });
    response.status = 200; // ok
});

router.get('/movie/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const item = await itemStore.findOne({ _id: ctx.params.id });
    const response = ctx.response;
    if (item) {
        if (item.userId === userId) {
            response.body = item;
            response.status = 200; // ok
        } else {
            response.status = 403; // forbidden
        }
    } else {
        response.status = 404; // not found
    }
});

const createItem = async (ctx, item, response) => {
    console.log("SE CREEAZA UN ITEM");
    try {
        const userId = ctx.state.user._id;
        item.userId = userId;
        response.body = await itemStore.insert(item);
        response.status = 201; // created
        broadcast(userId, { type: 'created', payload: response.body });
    } catch (err) {
        response.body = { message: err.message };
        response.status = 400; // bad request
    }
};

router.post('/movie', async ctx => await createItem(ctx, ctx.request.body, ctx.response));

router.put('/movie/:id', async (ctx) => {
    console.log("SE EDITEAZA UN ITEM");
    const item = ctx.request.body;
    const id = ctx.params.id;
    const itemId = item._id;
    const response = ctx.response;
    if (itemId && itemId !== id) {
        response.body = { message: 'Param id and body _id should be the same' };
        response.status = 400; // bad request
        return;
    }
    const userId = ctx.state.user._id;
    item.userId = userId;
    const updatedCount = await itemStore.update({ _id: id }, item);
    if (updatedCount === 1) {
        response.body = item;
        response.status = 200; // ok
        broadcast(userId, { type: 'updated', payload: item });
    } else {
        response.body = { message: 'Resource no longer exists' };
        response.status = 405; // method not allowed
    }
});

router.del('/movie/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const item = await itemStore.findOne({ _id: ctx.params.id });
    if (item && userId !== item.userId) {
        ctx.response.status = 403; // forbidden
    } else {
        await itemStore.remove({ _id: ctx.params.id });
        ctx.response.status = 204; // no content
    }
});
