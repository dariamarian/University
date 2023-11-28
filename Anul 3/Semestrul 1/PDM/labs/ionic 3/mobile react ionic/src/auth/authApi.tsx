import axios from "axios";
import {config, withLogs} from "../core";

const authUrl = `http://localhost:8080/api/auth/login`;

export interface AuthProps{
    id:number;
    username:string;
    token:string;
}

export const login: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
    return withLogs(axios.post(authUrl, { username, password }, config), 'login');
}