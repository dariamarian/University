import React, {useCallback, useContext, useState, useEffect, useReducer} from 'react';
import PropTypes from 'prop-types';
import {getLogger} from '../core';
import {ItemProps} from './ItemProps';
import {createItem, getItems, newWebSocket, editItem, syncData} from './itemApi';
import {AuthContext} from '../auth';
import {Plugins} from "@capacitor/core";

const log = getLogger('ItemProvider');

type SaveItemFn = (item: ItemProps) => Promise<any>;
const {Storage} = Plugins;

export interface ItemsState {
    items?: ItemProps[],
    fetching: boolean,
    fetchingError?: Error | null,
    saving: boolean,
    savingError?: Error | null,
    saveItem?: SaveItemFn,
    connectedNetworkStatus?: boolean,
    setSavedOffline?: Function,
    savedOffline?: boolean
}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: ItemsState = {
    fetching: false,
    saving: false,
    connectedNetworkStatus: false, // Add this line
};

const FETCH_ITEMS_STARTED = 'FETCH_ITEMS_STARTED';
const FETCH_ITEMS_SUCCEEDED = 'FETCH_ITEMS_SUCCEEDED';
const FETCH_ITEMS_FAILED = 'FETCH_ITEMS_FAILED';
const SAVE_ITEM_STARTED = 'SAVE_ITEM_STARTED';
const SAVE_ITEM_SUCCEEDED = 'SAVE_ITEM_SUCCEEDED';
const SAVE_ITEM_FAILED = 'SAVE_ITEM_FAILED';

const reducer: (state: ItemsState, action: ActionProps) => ItemsState =
    (state, {type, payload}) => {
        switch (type) {
            case FETCH_ITEMS_STARTED:
                return {...state, fetching: true, fetchingError: null};
            case FETCH_ITEMS_SUCCEEDED:
                return {...state, items: payload.items, fetching: false};
            case FETCH_ITEMS_FAILED:
                return {...state, fetchingError: payload.error, fetching: false};
            case SAVE_ITEM_STARTED:
                return {...state, savingError: null, saving: true};
            case SAVE_ITEM_SUCCEEDED:
                const items = [...(state.items || [])];
                const item = payload.item;
                const index = items.findIndex(it => it._id === item.id);
                if (index === -1) {
                    items.splice(0, 0, item);
                } else {
                    items[index] = item;
                }
                return {...state, items, saving: false};
            case SAVE_ITEM_FAILED:
                return {...state, savingError: payload.error, saving: false};
            default:
                return state;
        }
    };

export const ItemContext = React.createContext<ItemsState>(initialState);

interface ItemProviderProps {
    children: PropTypes.ReactNodeLike,
}

const {Network} = Plugins;

export const ItemProvider: React.FC<ItemProviderProps> = ({ children }) => {
    const { token } = useContext(AuthContext);

    const [connectedNetworkStatus, setConnectedNetworkStatus] = useState<boolean>(false);
    Network.getStatus().then(status => setConnectedNetworkStatus(status.connected));
    const [savedOffline, setSavedOffline] = useState<boolean>(false);
    useEffect(networkEffect, [token, setConnectedNetworkStatus]);


    const [state, dispatch] = useReducer(reducer, initialState);
    const { items, fetching, fetchingError, saving, savingError } = state;
    useEffect(getItemsEffect, [token]);
    useEffect(ws, [token]);
    const saveItem = useCallback<SaveItemFn>(saveItemCallback, [token]);
    const value = {
        items,
        fetching,
        fetchingError,
        saving,
        savingError,
        saveItem: saveItem,
        connectedNetworkStatus,
        savedOffline,
        setSavedOffline
    };
    return (
        <ItemContext.Provider value={value}>
            {children}
        </ItemContext.Provider>
    );

    function networkEffect() {
        console.log("network effect");
        let canceled = false;
        Network.addListener('networkStatusChange', async (status) => {
            if (canceled) return;
            const connected = status.connected;
            if (connected) {
                console.log("networkEffect - SYNC data");
                await syncData(token);
            }
            setConnectedNetworkStatus(status.connected);
        });
        return () => {
            canceled = true;
        }
    }

    function getItemsEffect() {
        let canceled = false;
        if (token) {
            fetchItems();
        }
        return () => {
            canceled = true;
        }
    }

    async function fetchItems() {
        let canceled = false;
        fetchItems();
        return () => {
            canceled = true;
        }

        async function fetchItems() {
            if (!token?.trim()) return;
            if (!navigator?.onLine) {
                let storageKeys = Storage.keys();
                const items = await storageKeys.then(async function (storageKeys) {
                    const saved = [];
                    for (let i = 0; i < storageKeys.keys.length; i++) {
                        if (storageKeys.keys[i] !== "token") {
                            const item = await Storage.get({ key: storageKeys.keys[i] });
                            if (item.value != null)
                                var parseditem = JSON.parse(item.value);
                            saved.push(parseditem);
                        }
                    }
                    return saved;
                });
                dispatch({ type: FETCH_ITEMS_SUCCEEDED, payload: { items: items } });
            } else {
                try {
                    log('fetchitems started');
                    dispatch({ type: FETCH_ITEMS_STARTED });
                    const items = await getItems(token);
                    log('fetchitems successful');
                    if (!canceled) {
                        dispatch({ type: FETCH_ITEMS_SUCCEEDED, payload: { items: items } })
                    }
                } catch (error) {
                    let storageKeys = Storage.keys();
                    const items = await storageKeys.then(async function (storageKeys) {
                        const saved = [];
                        for (let i = 0; i < storageKeys.keys.length; i++) {
                            if (storageKeys.keys[i] !== "token") {
                                const item = await Storage.get({ key: storageKeys.keys[i] });
                                if (item.value != null)
                                    var parseditem = JSON.parse(item.value);
                                saved.push(parseditem);
                            }
                        }
                        return saved;
                    });
                    dispatch({ type: FETCH_ITEMS_SUCCEEDED, payload: { items: items } });
                }
            }

        }
    }

    async function saveItemCallback(item: ItemProps) {
        try {
            if (navigator.onLine) {
                log('saveItem started');
                dispatch({type: SAVE_ITEM_STARTED});
                const updatedItem = await (item._id ? editItem(token, item) : createItem(token, item))
                log('saveItem successful');
                dispatch({type: SAVE_ITEM_SUCCEEDED, payload: {item: updatedItem}});
            } else {
                console.log('saveItem offline');
                log('saveItem failed');
                item._id = (item._id === undefined) ? ('_' + Math.random().toString(36).substr(2, 9)) : item._id;
                await Storage.set({
                    key: item._id!,
                    value: JSON.stringify({
                        _id: item._id,
                        title: item.title,
                        genre: item.genre,
                        releaseDate: item.releaseDate,
                        rating: item.rating,
                        watched: item.watched,
                    })
                });
                dispatch({type: SAVE_ITEM_SUCCEEDED, payload: {item: item}});
                setSavedOffline(true);
            }
        } catch (error) {
            log('saveItem failed');
            await Storage.set({
                key: String(item._id),
                value: JSON.stringify(item)
            })
            dispatch({type: SAVE_ITEM_SUCCEEDED, payload: {item: item}});
        }
    }

    function ws() {
        let canceled = false;
        log('wsEffect - connecting');
        let closeWebSocket: () => void;
        if (token?.trim()) {
            closeWebSocket = newWebSocket(token, message => {
                if (canceled) {
                    return;
                }
                const {type, payload: item} = message;
                log(`ws message, item ${type}`);
                if (type === 'created' || type === 'updated') {
                    dispatch({type: SAVE_ITEM_SUCCEEDED, payload: {item}});
                }
            });
        }
        return () => {
            log('wsEffect - disconnecting');
            canceled = true;
            closeWebSocket?.();
        }
    }
}