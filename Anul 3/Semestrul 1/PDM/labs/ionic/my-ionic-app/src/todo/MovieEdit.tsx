import React, { useCallback, useContext, useEffect, useState } from 'react';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonInput,
  IonLoading,
  IonPage,
  IonTitle,
  IonToolbar,
  IonCheckbox
} from '@ionic/react';
import { getLogger } from '../core';
import { MovieContext } from './MovieProvider';
import { RouteComponentProps } from 'react-router';
import { MovieProps } from './MovieProps';

const log = getLogger('MovieEdit');

interface MovieEditProps extends RouteComponentProps<{
  id?: string;
}> {}

const MovieEdit: React.FC<MovieEditProps> = ({ history, match }) => {
  const { movies, saving, savingError, saveMovie } = useContext(MovieContext);
  const [title, setTitle] = useState('');
  const [releaseDate, setReleaseDate] = useState('');
  const [rating, setRating] = useState(0);
  const [watched, setWatched] = useState(false);
  const [movie, setMovie] = useState<MovieProps>();
  useEffect(() => {
    log('useEffect');
    const routeId = match.params.id || '';
    const movie = movies?.find(m => m.id === routeId);
    setMovie(movie);
    if (movie) {
      setTitle(movie.title);
      setReleaseDate(movie.releaseDate);
      setRating(movie.rating);
      setWatched(movie.watched);
    }
  }, [match.params.id, movies]);
  const handleSave = useCallback(() => {
    const editedMovie = movie
        ? { ...movie, title, releaseDate, rating, watched }
        : { title, releaseDate, rating, watched };
    saveMovie && saveMovie(editedMovie).then(() => history.goBack());
  }, [movie, saveMovie, title, releaseDate, rating, watched, history]);
  log('render');
  return (
      <IonPage>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Edit</IonTitle>
            <IonButtons slot="end">
              <IonButton onClick={handleSave}>
                Save
              </IonButton>
            </IonButtons>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <IonInput value={title} onIonChange={e => setTitle(e.detail.value || '')} />
          <IonInput value={releaseDate} onIonChange={e => setReleaseDate(e.detail.value || '')} />
          <IonInput type="number" value={rating} onIonChange={e => setRating(parseInt(e.detail.value || '0'))} />
          <IonCheckbox checked={watched} onIonChange={e => setWatched(e.detail.checked)} />
          <IonLoading isOpen={saving} />
          {savingError && (
              <div>{savingError.message || 'Failed to save movie'}</div>
          )}
        </IonContent>
      </IonPage>
  );
};

export default MovieEdit;
