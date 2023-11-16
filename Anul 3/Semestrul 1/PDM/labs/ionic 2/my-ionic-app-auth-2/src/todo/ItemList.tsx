import React, {useContext, useState, useEffect} from 'react';
import {RouteComponentProps} from 'react-router';
import {
    IonContent,
    IonFab,
    IonFabButton,
    IonHeader,
    IonIcon,
    IonItem,
    IonList,
    IonLoading,
    IonPage,
    IonToolbar,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
    IonToast,
    IonListHeader,
    IonLabel,
    IonSelect,
    IonSelectOption,
    IonSearchbar,
    IonChip,
} from '@ionic/react';
import {add} from 'ionicons/icons';
import {getLogger} from '../core';
import {ItemContext} from './ItemProvider';
import {AuthContext} from "../auth";
import {Network} from '@capacitor/core';
import {ItemProps} from './ItemProps';
import Movie from './Movie';

const log = getLogger('ItemList');
const offset = 15;

const ItemList: React.FC<RouteComponentProps> = ({ history }) => {
    const { logout } = useContext(AuthContext)
    const { items, fetching, fetchingError } = useContext(ItemContext);
    const [disableInfiniteScroll, setDisabledInfiniteScroll] = useState<boolean>(false);
    const [visibleItems, setVisibleItems] = useState<ItemProps[] | undefined>([]);
    const [page, setPage] = useState(0)
    const [filter, setFilter] = useState<string | undefined>(undefined);
    const [search, setSearch] = useState<string>("");
    const [status, setStatus] = useState<boolean>(true);
    const { savedOffline, setSavedOffline } = useContext(ItemContext);

    Network.getStatus().then(status => setStatus(status.connected));
    log('render', fetching);

    Network.addListener('networkStatusChange', (status: { connected: any; }) => {
        setStatus(status.connected);
    })

    const genres = ["Adventure", "Sci-Fi", "Action", "Crime", "Romantic", "Drama", "Comedy", "Biography"];

    useEffect(() => {
        if (items?.length && items?.length > 0) {
            setPage(offset);
            fetchData();
            console.log(items);
        }
    }, [items]);

    useEffect(() => {
        if (items && filter) {
            setVisibleItems(items.filter(each => each.genre === filter));
        }
    }, [filter]);

    useEffect(() => {
        if (search === "") {
            setVisibleItems(items);
        }
        if (items && search !== "") {
            setVisibleItems(items.filter(each => each.title && each.title.startsWith(search)));
        }
    }, [search])

    function fetchData() {
        setVisibleItems(items?.slice(0, page + offset));
        setPage(page + offset);
        if (items && page > items?.length) {
            setDisabledInfiniteScroll(true);
            setPage(items.length);
        } else {
            setDisabledInfiniteScroll(false);
        }
    }


    async function searchNext($event: CustomEvent<void>) {
        await fetchData();
        ($event.target as HTMLIonInfiniteScrollElement).complete();
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonItem>
                        <IonSelect style={{width: '40%'}} value={filter} placeholder="Pick a genre"
                                   onIonChange={(e) => setFilter(e.detail.value)}>
                            {genres.map((each) => (
                                <IonSelectOption key={each} value={each}>
                                    {each}
                                </IonSelectOption>
                            ))}
                        </IonSelect>
                        <IonSearchbar style={{width: '50%'}} placeholder="Search by title" value={search} debounce={200}
                                      onIonChange={(e) => {
                                          setSearch(e.detail.value!);
                                      }}>
                        </IonSearchbar>
                        <IonChip>
                            <IonLabel color={status ? "success" : "danger"}>{status ? "Online" : "Offline"}</IonLabel>
                        </IonChip>
                    </IonItem>
                </IonToolbar>
            </IonHeader>

            <IonContent fullscreen>
                <IonLoading isOpen={fetching} message="This might take a moment..."/>

                {
                    visibleItems && (
                        <IonList>
                            <IonListHeader>
                                <IonLabel>Title</IonLabel>
                                <IonLabel>Genre</IonLabel>
                                <IonLabel>Release Date</IonLabel>
                                <IonLabel>Rating</IonLabel>
                                <IonLabel>Watched</IonLabel>
                            </IonListHeader>
                            {
                                Array.from(visibleItems)
                                    .filter(each => {
                                        if (filter !== undefined) {
                                            return each.genre === filter && each._id !== undefined;
                                        }
                                        return each._id !== undefined;
                                    })
                                    .map(({_id, title, genre, releaseDate, rating, watched}) =>
                                        <Movie key={_id} _id={_id} title={title} genre={genre} releaseDate={releaseDate}
                                                rating={rating} watched={watched || false}
                                                onEdit={_id => history.push(`/api/items/movie/${_id}`)}/>)}
                        </IonList>
                    )
                }

                <IonInfiniteScroll threshold="100px" disabled={disableInfiniteScroll}
                                   onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
                    <IonInfiniteScrollContent loadingText="Loading..."/>
                </IonInfiniteScroll>

                {
                    fetchingError && (
                        <div>{fetchingError.message || 'Failed to fetch items'}</div>
                    )
                }

                <IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton onClick={() => history.push('/api/items/movie')}>
                        <IonIcon icon={add}/>
                    </IonFabButton>
                </IonFab>

                <IonFab vertical="bottom" horizontal="start" slot="fixed">
                    <IonFabButton onClick={handleLogout}>
                        LOGOUT
                    </IonFabButton>
                </IonFab>
                <IonToast
                    isOpen={savedOffline ? true : false}
                    message="Your changes will be visible on server when you get back online!"
                    duration={2000}/>
            </IonContent>
        </IonPage>
    );

    function handleLogout() {
        log("logout");
        logout?.();
    }
};


export default ItemList;