import axios from 'axios';
import {authConfig, getLogger, withLogs} from '../core';
import {ItemProps} from './ItemProps';
import {Plugins} from '@capacitor/core';

const url = 'localhost:3000';
const baseUrl = `http://${url}/api/items`;
const {Storage} = Plugins;

const different = (item1: any, item2: any) => {
    if (item1.title === item2.title && item1.genre === item2.genre && item1.releaseDate === item2.releaseDate && item1.rating === item2.rating)
        return false;
    return true;
}

export const createItem: (token: string, item: ItemProps) => Promise<ItemProps[]> = (token, item) => {
    var result = axios.post(`${baseUrl}/movie`, item, authConfig(token));
    result.then(async result => {
        var one = result.data;
        await Storage.set({
            key: one._id!,
            value: JSON.stringify({
                _id: one._id,
                title: one.title,
                genre: one.genre,
                releaseDate: one.releaseDate,
                rating: one.rating,
                watched: one.watched,
            })
        });
    }).catch(err => {
        if (err.response) {
            console.log('client received an error response (5xx, 4xx)');
        } else if (err.request) {
            alert('client never received a response, or request never left');
        } else {
            console.log('anything else');
        }
    });
    return withLogs(result, 'createItem');
}

export const editItem: (token: string, item: ItemProps) => Promise<ItemProps[]> = (token, item) => {
    var result = axios.put(`${baseUrl}/movie/${item._id}`, item, authConfig(token));
    result.then(async result => {
        var one = result.data;
        await Storage.set({
            key: one._id!,
            value: JSON.stringify({
                _id: one._id,
                title: one.title,
                genre: one.genre,
                releaseDate: one.releaseDate,
                rating: one.rating,
                watched: one.watched,
            })
        }).catch((err) => {
            if (err.response) {
                alert('client received an error response (5xx, 4xx)');
            } else if (err.request) {
                alert('client never received a response, or request never left');
            } else {
                alert('anything else');
            }
        })
    });
    return withLogs(result, 'updateItem');
}

interface MessageData {
    type: string;
    payload: ItemProps;
}

const log = getLogger('ws');

export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
    const ws = new WebSocket(`ws://${url}`);
    ws.onopen = () => {
        log('web socket onopen');
        ws.send(JSON.stringify({type: 'authorization', payload: {token}}));
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

export const getItems: (token: string) => Promise<ItemProps[]> = token => {
    try {
        var result = axios.get(`${baseUrl}/movies`, authConfig(token));
        result.then(async result => {
            for (const each of result.data) {
                await Storage.set({
                    key: each._id!,
                    value: JSON.stringify({
                        _id: each._id,
                        title: each.title,
                        genre: each.genre,
                        releaseDate: each.releaseDate,
                        rating: each.rating,
                        watched: each.watched,
                    })
                });
            }
        }).catch(err => {
            if (err.response) {
                console.log('client received an error response (5xx, 4xx)');
            } else if (err.request) {
                console.log('client never received a response, or request never left');
            } else {
                console.log('anything else');
            }
        })
        return withLogs(result, 'getItems');
    } catch (error) {
        throw error;
    }
}


export const syncData: (token: string) => Promise<ItemProps[]> = async token => {
    try {
        const { keys } = await Storage.keys();
        var result = axios.get(`${baseUrl}/movies`, authConfig(token));
        result.then(async result => {
            keys.forEach(async (i: string) => {
                if (i !== 'token') {
                    const itemOnServer = result.data.find((each: { _id: string; }) => each._id === i);
                    const itemLocal = await Storage.get({ key: i });

                    console.log('item ON SERVER: ' + JSON.stringify(itemOnServer));
                    console.log('item LOCALLY: ' + itemLocal.value!);

                    if (itemOnServer !== undefined && different(itemOnServer, JSON.parse(itemLocal.value!))) {  // actualizare
                        console.log('UPDATE ' + itemLocal.value);
                        axios.put(`${baseUrl}/movie/${i}`, JSON.parse(itemLocal.value!), authConfig(token));
                    } else if (itemOnServer === undefined) {  // creare
                        console.log('CREATE' + itemLocal.value!);
                        axios.post(`${baseUrl}/movie`, JSON.parse(itemLocal.value!), authConfig(token));
                    }
                }
            })
        }).catch(err => {
            if (err.response) {
                console.log('client received an error response (5xx, 4xx)');
            } else if (err.request) {
                console.log('client never received a response, or request never left');
            } else {
                console.log('anything else');
            }
        })
        return withLogs(result, 'syncItems');
    } catch (error) {
        throw error;
    }
}