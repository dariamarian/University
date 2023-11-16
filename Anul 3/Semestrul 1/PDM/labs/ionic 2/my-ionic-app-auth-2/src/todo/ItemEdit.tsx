import React, {useContext, useEffect, useState} from 'react';
import {
    IonButton,
    IonButtons,
    IonContent,
    IonHeader,
    IonInput,
    IonItem,
    IonLabel,
    IonList,
    IonLoading,
    IonPage,
    IonSelect,
    IonSelectOption,
    IonText,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import {getLogger} from '../core';
import {ItemContext} from './ItemProvider';
import {RouteComponentProps} from 'react-router';
import {ItemProps} from './ItemProps';

const log = getLogger('ItemEdit');

interface ItemEditProps extends RouteComponentProps<{
    id?: string;
}> {
}

const ItemEdit: React.FC<ItemEditProps> = ({history, match}) => {
    const {items, saving, savingError, saveItem} = useContext(ItemContext);
    const [title, setTitle] = useState('');
    const [genre, setGenre] = useState('');
    const [releaseDate, setReleaseDate] = useState('');
    const [rating, setRating] = useState(0);
    const [watched, setWatched] = useState(false);
    const [item, setItem] = useState<ItemProps>();
    useEffect(() => {
        log('useEffect');
        const routeId = match.params.id || '';
        const item = items?.find(it => it._id === routeId);
        setItem(item);
        if (item) {
            setTitle(item.title);
            setGenre(item.genre);
            setReleaseDate(item.releaseDate);
            setRating(item.rating);
            setWatched(item.watched);
        }
    }, [match.params.id, items]);
    const handleSave = () => {
        const editedItem = item ? {...item, title, genre, releaseDate, rating, watched} : {
            title,
            genre,
            releaseDate,
            rating,
            watched
        };
        saveItem && saveItem(editedItem).then(() => history.goBack());
    };
    log('render');
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Edit Player</IonTitle>
                    <IonButtons slot="end">
                        <IonButton onClick={handleSave}>Save</IonButton>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <IonList>
                    <IonItem>
                        <IonLabel position="floating">Title</IonLabel>
                        <IonInput value={title} onIonChange={e => setTitle(e.detail.value || '')}/>
                    </IonItem>
                    <IonItem>
                        <IonLabel position="floating">Genre</IonLabel>
                        <IonInput value={genre} onIonChange={e => setGenre(e.detail.value || '')}/>
                    </IonItem>
                    <IonItem>
                        <IonLabel position="floating">Release Date</IonLabel>
                        <IonInput value={releaseDate} onIonChange={e => setReleaseDate(e.detail.value || '')}
                                  placeholder="YYYY-MM-DD"/>
                    </IonItem>
                    <IonItem>
                        <IonLabel position="floating">Rating</IonLabel>
                        <IonInput value={rating} onIonChange={e => setRating(parseInt(e.detail.value || '0'))}
                                  placeholder="Rating"/>
                    </IonItem>
                    <IonItem>
                        <IonLabel>Watched</IonLabel>
                        <IonSelect value={watched} onIonChange={(e) => setWatched(e.detail.value)}>
                            <IonSelectOption value={true}>True</IonSelectOption>
                            <IonSelectOption value={false}>False</IonSelectOption>
                        </IonSelect>
                    </IonItem>
                </IonList>
                <IonLoading isOpen={saving} message="Saving movie"/>
                {savingError && (
                    <IonText color="danger">{savingError.message || 'Failed to save movie'}</IonText>
                )}
            </IonContent>
        </IonPage>
    );
};

export default ItemEdit;