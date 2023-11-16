import React, { memo } from 'react';
import { IonItem, IonLabel } from '@ionic/react';
import { MovieProps } from './MovieProps';

interface MoviePropsExt extends MovieProps {
    onEdit: (id?: string) => void;
}

const Movie: React.FC<MoviePropsExt> = ({ id, title, releaseDate, rating, watched, onEdit }) => {
    return (
        <IonItem onClick={() => onEdit(id)}>
            <IonLabel>Title: {title}</IonLabel>
            <IonLabel>Release Date: {releaseDate}</IonLabel>
            <IonLabel>Rating: {rating}</IonLabel>
            <IonLabel>Watched: {watched ? 'Yes' : 'No'}</IonLabel>
        </IonItem>
    );
};

export default memo(Movie);
