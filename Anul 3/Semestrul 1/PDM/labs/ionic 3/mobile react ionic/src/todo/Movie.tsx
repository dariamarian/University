import React, {memo} from 'react';
import {IonItem, IonLabel} from '@ionic/react';
import {getLogger} from '../core';
import {MovieProps} from './MovieProps';

const log = getLogger('Movie');

interface ItemPropsExt extends MovieProps {
    onEdit: (id?: string) => void;
}

const Movie: React.FC<ItemPropsExt> = ({title, rating, watched, date, onEdit}) => {
    return (
        <IonItem onClick={() => onEdit(title)}>
            <IonLabel>{title}</IonLabel>
            <IonLabel>{rating}</IonLabel>
            <IonLabel>{watched.toString()}</IonLabel>
            <IonLabel>{date.toISOString()}</IonLabel>
        </IonItem>
    );
};

export default memo(Movie);
