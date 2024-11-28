import React from "react";
import {useAuth} from "./auth-provider";
import {Navigate, Outlet} from "react-router";

const LOGIN_SCREEN_ROUTE = "/login";

export const ProtectedRoute = () => {
    const { isLoggedIn } = useAuth();

    if (!isLoggedIn()) {
        return <Navigate to={LOGIN_SCREEN_ROUTE} />;
    }

    return <Outlet />;
};
