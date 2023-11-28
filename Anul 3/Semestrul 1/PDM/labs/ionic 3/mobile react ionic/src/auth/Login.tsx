import {useCallback, useContext, useEffect, useState } from "react";
import {getLogger} from "../core";
import {AuthContext} from "./AuthProvider";
import { RouteComponentProps } from "react-router";
import {IonButton, IonContent, IonHeader, IonInput, IonLoading, IonPage, IonTitle, IonToolbar } from "@ionic/react";
import {useNetwork} from "../todo/useNetwork";


interface LoginState{
    username?: string;
    password?: string;
}

const log = getLogger('Login');

export const Login: React.FC<RouteComponentProps> = ({ history }) => {
    const { isAuthenticated, isAuthenticating, login, authenticationError } = useContext(AuthContext);
    const [state, setState] = useState<LoginState>( {});
    const { networkStatus } = useNetwork();
    const { username, password } = state;
    const isOnline = networkStatus.connected;
    const handlePasswwordChange = useCallback((e: any) => setState({
        ...state,
        password: e.detail.value || ''
    }), [state]);
    const handleUsernameChange = useCallback((e: any) => setState({
        ...state,
        username: e.detail.value || ''
    }), [state]);
    const handleLogin = useCallback(() => {
        log('handleLogin...');
        login?.(username, password);
    }, [username, password]);
    log('render');
    useEffect(() => {
        if (isAuthenticated || localStorage.getItem("token")) {
            log('redirecting to home');
            history.push('/movies');
        }
    }, [isAuthenticated]);
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Login</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <div>
                    <p>Network status is <span style={{ color: isOnline ? 'green' : 'red' }}>{isOnline ? 'online' : 'offline'}</span></p>
                </div>
                <IonInput
                    placeholder="Username"
                    value={username}
                    onIonChange={handleUsernameChange}/>
                <IonInput
                    placeholder="Password"
                    value={password}
                    onIonChange={handlePasswwordChange}/>
                <IonLoading isOpen={isAuthenticating}/>
                {authenticationError && (
                    <div>{authenticationError.message || 'Failed to authenticate'}</div>
                )}
                <IonButton onClick={handleLogin}>Login</IonButton>
            </IonContent>
        </IonPage>
    );
};