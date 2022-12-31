import {createBrowserRouter} from "react-router-dom";
import React from "react";
import Login from "../views/authorization/Login";
import Home from "../views/Home";
import Registration from "../views/authorization/Registration";
import ProtectedElement from "../views/authorization/ProtectedElement";
import PostBuilder from "../views/post/PostBuilder";
import {CopyNavbar} from "../components/CopyNavbar";
import UserBoardView from "../views/board/UserBoardView";
import Post from "../views/post/Post";

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
                path: "post/:postId",
                element: <ProtectedElement children={<Post />} />
            },
            {
                path: "post-builder",
                element: <ProtectedElement children={<PostBuilder />} />
            },
            {
                path: ":username/:minimizedName",
                element: <UserBoardView />
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
