import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import router from "./router/Router";
import {RouterProvider} from "react-router-dom";
import {ChakraProvider} from "@chakra-ui/react";
import {store} from "./redux/store";
import {Provider} from "react-redux";
import Navbar from "./components/Navbar";
import {CopyNavbar} from "./components/CopyNavbar";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <ChakraProvider>
                {/*<CopyNavbar />*/}
                <RouterProvider router={router} />
            </ChakraProvider>
        </Provider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
