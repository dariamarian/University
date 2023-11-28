import {Redirect, Route} from 'react-router-dom';
import {IonApp, IonRouterOutlet, setupIonicReact} from '@ionic/react';
import {IonReactRouter} from '@ionic/react-router';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';

import {MovieEdit, MovieList} from './todo';
import {MovieProvider} from './todo/MovieProvider';
import {AuthProvider} from "./auth/AuthProvider";
import {Login} from "./auth/Login";
import {PrivateRoute} from "./auth/PrivateRoute";

setupIonicReact();

const App: React.FC = () => (
    <IonApp>
        <IonReactRouter>
            <IonRouterOutlet>
                <AuthProvider>
                    <Route path="/login" component={Login} exact={true}/>
                    <MovieProvider>
                        <PrivateRoute path="/movies" component={MovieList} exact={true}/>
                        <PrivateRoute path="/movie" component={MovieEdit} exact={true}/>
                        <PrivateRoute path="/movie/:id" component={MovieEdit} exact={true}/>
                    </MovieProvider>
                    <Route exact path="/" render={() => <Redirect to="/movies"/>}/>
                </AuthProvider>
            </IonRouterOutlet>
        </IonReactRouter>
    </IonApp>
);

export default App;
