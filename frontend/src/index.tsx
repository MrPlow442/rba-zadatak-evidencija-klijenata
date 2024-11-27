import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {CardRequestDetails} from "./card-request/card-request-details/card-request-details";
import {CardRequestList} from "./card-request/card-request-list/card-request-list";
import {RouteConstants, RouteParams} from "./constants/route-constants";
import {SnackbarProvider} from "notistack";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
      <SnackbarProvider>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<Navigate to="/card-requests" replace />} />
                  <Route path={RouteConstants.cardRequests} element={<CardRequestList />} />
                  <Route path={`${RouteConstants.cardRequests}/${RouteConstants.modifiers.new}`} element={<CardRequestDetails/>} />
                  <Route path={`${RouteConstants.cardRequests}/${RouteConstants.modifiers.edit}/${RouteParams.oib}`} element={<CardRequestDetails />} />
              </Routes>
          </BrowserRouter>
      </SnackbarProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
