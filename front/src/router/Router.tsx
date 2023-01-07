import {createBrowserRouter} from "react-router-dom";
import React from "react";
import Login from "../views/authorization/Login";
import Home from "../views/Home";
import Registration from "../views/authorization/Registration";
import ProtectedElement from "../views/authorization/ProtectedElement";
import PostBuilder from "../views/post/PostBuilder";
import {CopyNavbar} from "../components/CopyNavbar";
import UserBoardView, {SpecialUserBoardView} from "../views/board/UserBoardView";
import Post from "../views/post/Post";
import UserProfile from "../views/user/UserProfile";

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
                path: ":username/posts",
                element: <UserBoardView isSpecial={SpecialUserBoardView.ALL_POSTS} />
            },
            {
                path: ":username/liked",
                element: <UserBoardView isSpecial={SpecialUserBoardView.LIKED}/>
            },
            {
                path: ":username/:minimizedName",
                element: <UserBoardView isSpecial={SpecialUserBoardView.DEFAULT}/>
            },
            {
                path: ":username",
                element: <UserProfile />
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
