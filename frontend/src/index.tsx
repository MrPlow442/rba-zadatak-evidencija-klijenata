import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {CardRequestDetails} from "./card-request/card-request-details/card-request-details";
import {CardRequestList} from "./card-request/card-request-list/card-request-list";
import {RouteConstants, RouteParams} from "./constants/route-constants";
import {SnackbarProvider} from "notistack";
import {AuthProvider} from "./auth/auth-provider";
import {Login} from "./auth/login";
import {ProtectedRoute} from "./auth/protected-route";
import {Layout} from "./components/layout";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
      <AuthProvider>
          <SnackbarProvider>
              <BrowserRouter>
                  <Routes>
                      <Route path="/" element={<Navigate to={`/${RouteConstants.cardRequests}`} replace />} />
                      <Route path={RouteConstants.login} element={<Login />} />

                      <Route element={<Layout />}>
                          <Route element={<ProtectedRoute />}>
                              <Route path={RouteConstants.cardRequests} element={<CardRequestList />} />
                              <Route path={`${RouteConstants.cardRequests}/${RouteConstants.modifiers.new}`} element={<CardRequestDetails/>} />
                              <Route path={`${RouteConstants.cardRequests}/${RouteConstants.modifiers.edit}/${RouteParams.oib}`} element={<CardRequestDetails />} />
                          </Route>
                      </Route>
                  </Routes>
              </BrowserRouter>
          </SnackbarProvider>
      </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
