import React, {useContext, useEffect, useState} from 'react';
import {RouteComponentProps} from 'react-router';
import {
    IonButton,
    IonContent,
    IonFab,
    IonFabButton,
    IonHeader,
    IonIcon,
    IonImg,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
    IonItem,
    IonLabel,
    IonList,
    IonPage,
    IonSearchbar,
    IonSelect,
    IonSelectOption,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import {add} from 'ionicons/icons';
import {getLogger} from '../core';
import {MovieContext, MoviesState} from './MovieProvider';
import {useNetwork} from "./useNetwork";
import {getMovies} from "./MovieApi";
import {MovieProps} from "./MovieProps";
import MyMap from "../maps/MyMap";
import {MyModal} from "../animation/MyModal";

const log = getLogger('MovieList');

// export interface PageState{
//     page: number,
//     pageSize: number
// }
//
// const initialState : PageState ={
//     page:0,
//     pageSize:10,
// };
//export const PageContext = React.createContext<PageState>(initialState);

const MovieList: React.FC<RouteComponentProps> = ({history}) => {
    //const [pageState, setPageState] = useState<PageState>(initialState);
    const [filter, setFilter] = useState<string | undefined>(undefined);
    const [searchTitle, setSearchTitle] = useState<string>('');
    const {movies, fetching, fetchingError,setPageSize,pageSize} = useContext(MovieContext);
    const { networkStatus } = useNetwork();
    const isOnline = networkStatus.connected;
    const [disableInfiniteScroll, setDisableInfiniteScroll] = useState(false);
    //const { page, pageSize } = useContext(PageContext);
    log('render');



    const handleLogout = () => {
        localStorage.removeItem('token');
        history.push('/login');
    };
    //const movies = await getMovies(localStorage.getItem("token") || "", pageState.page , pageState.pageSize);
    //setMovies(await getMovies(localStorage.getItem("token") || "", pageState.page, pageState.pageSize));
    async function searchNext($event: CustomEvent<void>) {
        setPageSize(pageSize+pageSize);
        await ($event.target as HTMLIonInfiniteScrollElement).complete();
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>My App</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <div>
                    <p>Network status is <span style={{ color: isOnline ? 'green' : 'red' }}>{isOnline ? 'online' : 'offline'}</span></p>
                </div>
                <IonButton onClick={handleLogout}>Logout</IonButton>
                <IonSearchbar
                    value={searchTitle}
                    debounce={1000}
                    onIonInput={e => setSearchTitle(e.detail.value!)}>
                </IonSearchbar>
                <IonSelect
                    value={filter}
                    placeholder="Select watched"
                    onIonChange={(e) => {
                        setFilter(e.detail.value);
                    }}
                >
                    <IonSelectOption key="all" value="all">
                        All
                    </IonSelectOption>
                    <IonSelectOption key="true" value="true">
                        Yes
                    </IonSelectOption>
                    <IonSelectOption key="false" value="false">
                        No
                    </IonSelectOption>
                </IonSelect>
                {/*<IonLoading isOpen={fetching} message="Fetching movies"/>*/}
                {movies && (
                    <IonList>
                        {movies.filter((movie) => {
                            const titleMatch = movie.title.includes(searchTitle);
                            const retiredMatch =
                                filter === undefined || filter === 'all'
                                    ? true
                                    : movie.watched.toString() === filter;
                            return titleMatch && retiredMatch;
                        })
                            .map(({ title, rating, watched, date, picture, lat, lng }) => (
                            <IonItem key={title} onClick={() => history.push(`/movies/${title}`)}
                                     style={{ color: isOnline ? 'inherit' : 'red' }}
                            >
                                <IonLabel>{title}</IonLabel>
                                <IonLabel>Rating: {rating}</IonLabel>
                                <IonLabel>Watched: {watched ? 'Yes' : 'No'}</IonLabel>
                                <IonLabel>{new Date(date).toLocaleDateString()}</IonLabel>
                                {lat && lng && <MyMap lat={lat} lng={lng} size={{ width: '150px', height: '150px' }} />}
                                {picture && <IonImg src={`data:image/jpeg;base64,${picture}`} alt="Movie" style={{ width: '100px', height: '100px' }} />}
                            </IonItem>
                        ))}

                    </IonList>
                )}
                <IonInfiniteScroll threshold="100px" disabled={disableInfiniteScroll}
                                   onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
                    <IonInfiniteScrollContent
                        loadingText="Loading more movies...">
                    </IonInfiniteScrollContent>
                </IonInfiniteScroll>


                {fetchingError && (
                    <div>{fetchingError.message || 'Failed to fetch movies'}</div>
                )}
                <MyModal></MyModal>
                {/*<IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton onClick={() => history.push('/movie')}>
                        <IonIcon icon={add}/>
                    </IonFabButton>
                </IonFab>*/}
            </IonContent>
        </IonPage>
    );
};

export default MovieList;
