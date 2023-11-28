import React, {useCallback, useContext, useEffect, useState} from 'react';
import {
  IonButton,
  IonButtons,
  IonCheckbox,
  IonContent,
  IonDatetime,
  IonFab,
  IonFabButton,
  IonHeader,
  IonIcon,
  IonInput,
  IonItem,
  IonLabel,
  IonLoading,
  IonPage,
  IonText,
  IonTitle,
  IonToolbar,
  createAnimation,
  useIonAlert
} from '@ionic/react';
import {getLogger} from '../core';
import {MovieContext} from './MovieProvider';
import {RouteComponentProps} from 'react-router';
import { camera, close, trash } from 'ionicons/icons';
import { usePhotos } from '../camera/usePhotos';
import MyMap from '../maps/MyMap';
import { useMyLocation } from '../maps/useMyLocation';


const log = getLogger('MovieEdit');

interface MovieEditProps {
  closeModal: () => void;
}

const MovieEdit: React.FC<MovieEditProps> = ({ closeModal }) => {
  const { photos, takePhoto, deletePhoto } = usePhotos();
  const { movies, saving, savingError, saveMovie } = useContext(MovieContext);
  const myLocation = useMyLocation();
  const { latitude: lat, longitude: lng } = myLocation.position?.coords || {}
  const [titleError, setTitleError] = useState(false);
  const [movie, setMovie] = useState({
    title: '',
    rating: 0,
    watched: false,
    date: new Date(),
    username: '',
    picture: '',
    lat : 0,
    lng : 0
  });

  const onAnimationFinish = () => {
    setTitleError(false);
  };
  function simpleAnimationJS() {
    const el = document.getElementById("2") as HTMLElement;
    console.log(el)
    if (el) {
      //el.style.color = 'red'
      const animation = createAnimation()
          .addElement(el)
          .duration(500)
          .keyframes([
            { offset: 0, transform: 'translateX(0)', opacity: '1' },
            { offset: 0.25, transform: 'translateX(-10px)', opacity: '1' },
            { offset: 0.5, transform: 'translateX(10px)', opacity: '1' },
            { offset: 0.75, transform: 'translateX(-10px)', opacity: '1' },
            { offset: 1, transform: 'translateX(0)', opacity: '1' },
          ]);
      animation.play();
    }
  };

  /*useEffect(() => {
    log('useEffect');
    //const routeId = match.params.title || '';
    const movie = movies?.find(kb => kb.title === routeId);
    console.log(movie);
    if (movie) {
      setMovie(movie);
    }
  }, [match.params.title, movies]);*/

  const handleTakePhoto = async () => {
    const photo = await takePhoto();
    console.log("photo");
    setMovie({ ...movie, picture: photo || ''});
  };

  const handleSave = useCallback(async () => {
    console.log(movie.title);
    if (movie.title == '') {
      console.log("gfsfgasfasafsfdasfda")
      // Show title error animation if it's empty
      setTitleError(true);
      simpleAnimationJS(); // Run the animation on error
      return;
    }

    saveMovie && saveMovie(movie).then(() => {
      closeModal();
    })
  }, [saveMovie, movie, history]);
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

        <IonLabel >Title:</IonLabel>
        <IonInput
            id = "2"
            placeholder = 'Title'
            value={movie.title}
            onIonChange={e => setMovie({ ...movie, title: e.detail.value || '' })}

        />
        <IonLabel>Rating:</IonLabel>
        <IonInput
            value={movie.rating}
            type="number"
            onIonChange={e => setMovie({ ...movie, rating: parseInt(e.detail.value || '') })}
            placeholder="Rating"
        />
        <IonLabel>Date:</IonLabel>
        <IonDatetime
            value={movie.date.toISOString()}
            onIonChange={e => setMovie({ ...movie,date : new Date(e.detail.value as string)})}
        />
        <IonLabel>Watched:</IonLabel>
        <IonCheckbox
            checked={movie.watched}
            onIonChange={e => setMovie({ ...movie, watched: e.detail.checked })}
        />
        {lat && lng &&
            <MyMap
                lat={lat}
                lng={lng}
                onUpdateLocation={(newLat, newLng) => setMovie({ ...movie, lat: newLat, lng: newLng })}
            />}
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton onClick={handleTakePhoto}>
            <IonIcon icon={camera}/>
          </IonFabButton>
        </IonFab>
        <IonLoading isOpen={saving} />
        {savingError && (
          <div>{savingError.message || 'Failed to save movie'}</div>
        )}
      </IonContent>
    </IonPage>
  );
};

export default MovieEdit;
