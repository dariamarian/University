import React, { memo } from 'react';
import { IonItem, IonLabel } from '@ionic/react';
import { getLogger } from '../core';
import { MovieProps } from './MovieProps';

const log = getLogger('Movie');

interface MoviePropsExt extends MovieProps {
    onEdit: (id?: string) => void;
}

const Movie: React.FC<MoviePropsExt> = ({ id, title, releaseDate, rating, watched, onEdit }) => {
    return (
        <IonItem onClick={() => onEdit(id)}>
            <IonLabel>{title}</IonLabel>
            <IonLabel>Release Date: {releaseDate}</IonLabel>
            <IonLabel>Rating: {rating}</IonLabel>
            <IonLabel>Watched: {watched ? 'Yes' : 'No'}</IonLabel>
        </IonItem>
    );
};

export default memo(Movie);
