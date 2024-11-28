import axios from "axios";
import {createContext, ReactNode, useContext, useEffect, useState} from "react";
import {Login, LoginUser} from "./auth-types";

const BASE_URL = 'http://localhost:8080/auth/login';
const LOCAL_STORAGE_KEY = 'current';

interface AuthContextProps {
    current: Login | null;
    login: (data: LoginUser) => Promise<void>;
    logout: () => void;
    isLoggedIn: () => boolean;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [current, setCurrent] = useState<Login | null>(getCurrentFromLocalStorage());

    const login = async (data: LoginUser): Promise<void> => {
        const response = await axios.post<Login>(BASE_URL, data);
        setCurrent(response.data);
        handleDefaultsAndStorage(response.data);
    };

    const logout = () => {
        setCurrent(null);
        handleDefaultsAndStorage(null);
    };

    const isLoggedIn = (): boolean => !!current;

    const handleDefaultsAndStorage = (current: Login | null) => {
        if (current) {
            axios.defaults.headers.common["Authorization"] = "Bearer " + current.token;
            localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(current));
        } else {
            delete axios.defaults.headers.common["Authorization"];
            localStorage.removeItem(LOCAL_STORAGE_KEY);
        }
    };

    useEffect(() => {
        handleDefaultsAndStorage(current);
    }, [current]);

    return (
        <AuthContext.Provider value={{ current, login, logout, isLoggedIn }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};

const getCurrentFromLocalStorage = (): Login | null => {
    const localStorageValue = localStorage.getItem(LOCAL_STORAGE_KEY);
    if (localStorageValue != null) {
        return JSON.parse(localStorageValue);
    }
    return null;
};
