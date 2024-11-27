import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {ClientDetails} from "./client/client-details/client-details";
import {ClientList} from "./client/client-list/client-list";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Navigate to="/clients" replace />} />
              <Route path="clients" element={<ClientList />} />
              <Route path="clients/new" element={<ClientDetails/>} />
              <Route path="clients/edit/:oib" element={<ClientDetails />} />
          </Routes>
      </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
