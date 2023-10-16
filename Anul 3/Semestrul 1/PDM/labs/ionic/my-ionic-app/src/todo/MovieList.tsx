import React, { useContext } from 'react';
import { RouteComponentProps } from 'react-router';
import {
  IonContent,
  IonFab,
  IonFabButton,
  IonHeader,
  IonIcon,
  IonList,
  IonLoading,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/react';
import { add } from 'ionicons/icons';
import Movie from './Movie'; // Import Movie instead of Book
import { getLogger } from '../core';
import { MovieContext } from './MovieProvider'; // Use MovieContext instead of BookContext

const log = getLogger('MovieList');

const MovieList: React.FC<RouteComponentProps> = ({ history }) => {
  const { movies, fetching, fetchingError } = useContext(MovieContext); // Use MovieContext instead of BookContext
  log('render');
  return (
      <IonPage>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Movie List</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <IonLoading isOpen={fetching} message="Fetching movies" />
          {movies && (
              <IonList>
                {movies.map(({ id, title, releaseDate, rating, watched }) =>
                    <Movie key={id} id={id} title={title} releaseDate={releaseDate} rating={rating} watched={watched} onEdit={id => history.push(`/movie/${id}`)} />)} {/* Modify the Movie props */}
              </IonList>
          )}
          {fetchingError && (
              <div>{fetchingError.message || 'Failed to fetch movies'}</div>
          )}
          <IonFab vertical="bottom" horizontal="end" slot="fixed">
            <IonFabButton onClick={() => history.push('/movie')}>
              <IonIcon icon={add} />
            </IonFabButton>
          </IonFab>
        </IonContent>
      </IonPage>
  );
};

export default MovieList;
