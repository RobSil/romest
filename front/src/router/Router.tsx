import {createBrowserRouter} from "react-router-dom";
import React, {FC, useState} from "react";
import {useAppSelector} from "../redux/reduxUtil";
import {useSelector} from "react-redux";
import {TestHome} from "./TestHome";
import Login from "../views/authorization/Login";
import Home from "../views/Home";
import Registration from "../views/authorization/Registration";
import ProtectedElement from "../views/authorization/ProtectedElement";
import PostBuilder from "../views/post/PostBuilder";
import {CopyNavbar} from "../components/CopyNavbar";

//<CopyNavbar />

// const router = createBrowserRouter([
//     {
//         path: "/post-builder",
//         element: <ProtectedElement children={<PostBuilder />} />
//     },
//     {
//         path: "/post-builder",
//         element: <ProtectedElement children={<PostBuilder />} />
//     },
//     {
//         path: "/home",
//         element: <ProtectedElement children={<Home />} />
//     },
//     {
//         path: "/login",
//         element: <Login />
//     },
//     {
//         path: "/register",
//         element: <Registration />
//     },
//     {
//         path: "/",
//         element: <TestHome />
//     },
//
// ])

const router = createBrowserRouter([
    {
        path: "/",
        element: <CopyNavbar />,
        children: [
            {
                path: "post-builder",
                element: <ProtectedElement children={<PostBuilder />} />
            },
            {
                path: "{username}/{minimizedName}",
                element: <ProtectedElement children={<PostBuilder />} />
            },
            {
                path: "post-builder",
                element: <ProtectedElement children={<PostBuilder />} />
            },
            {
                path: "home",
                element: <ProtectedElement children={<Home />} />
            },
            {
                path: "login",
                element: <Login />
            },
            {
                path: "register",
                element: <Registration />
            },
        ]
    },

])

export default router