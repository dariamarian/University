import {IonItem, IonLabel} from '@ionic/react';
import {getLogger} from '../core';
import {ItemProps} from './ItemProps';
import React, {useEffect} from 'react';

const log = getLogger('Movie');

interface ItemPropsExt extends ItemProps {
    onEdit: (_id?: string) => void;
}

const Movie: React.FC<ItemPropsExt> = ({_id, title, genre, releaseDate, rating, watched, onEdit}) => {
    useEffect(() => {
        document.getElementById("item")!.addEventListener("click", () => {
            onEdit(_id);
        });
    }, [document.getElementById("item")]);


    return (
        <IonItem id="item">
            <IonLabel>Title: {title} | Genre: {genre} | ReleaseDate: {new Date(releaseDate).toLocaleString()} |
                Rating: {rating} | Watched: {watched ? 'Yes' : 'No'}</IonLabel>
        </IonItem>
    );
};

export default Movie;
