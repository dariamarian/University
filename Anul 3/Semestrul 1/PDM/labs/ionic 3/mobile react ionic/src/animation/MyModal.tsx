import React, { useState } from 'react';
import { createAnimation, IonModal, IonButton, IonContent, IonFab, IonFabButton, IonIcon } from '@ionic/react';
import {MovieEdit} from "../todo";
import { add, remove } from 'ionicons/icons';

export const MyModal: React.FC = () => {
  const [showModal, setShowModal] = useState(false);

  const enterAnimation = (baseEl: any) => {
    const root = baseEl.shadowRoot;
    const backdropAnimation = createAnimation()
      .addElement(root.querySelector('ion-backdrop')!)
      .fromTo('opacity', '0.01', 'var(--backdrop-opacity)');

    const wrapperAnimation = createAnimation()
      .addElement(root.querySelector('.modal-wrapper')!)
      .keyframes([
        { offset: 0, opacity: '0', transform: 'scale(0)' },
        { offset: 1, opacity: '0.99', transform: 'scale(1)' }
      ]);

    return createAnimation()
      .addElement(baseEl)
      .easing('ease-out')
      .duration(500)
      .addAnimation([backdropAnimation, wrapperAnimation]);
  }

  const leaveAnimation = (baseEl: any) => {
    return enterAnimation(baseEl).direction('reverse');
  }

    const closeModal = () => {
        setShowModal(false);
    };

  console.log('MyModal', showModal);
  return (
    <>
      <IonModal isOpen={showModal} enterAnimation={enterAnimation} leaveAnimation={leaveAnimation}>
          <MovieEdit   closeModal={() => setShowModal(false)}/>
        {/*<IonButton onClick={() => setShowModal(false)}>Close Modal</IonButton>*/}
          <IonFab vertical="bottom" horizontal="start" slot="fixed">
              <IonFabButton onClick={closeModal}>
                  <IonIcon icon={remove}/>
              </IonFabButton>
          </IonFab>
      </IonModal>
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
            <IonFabButton onClick={() => setShowModal(true)}>
                <IonIcon icon={add}/>
            </IonFabButton>
        </IonFab>

    </>
  );
};
