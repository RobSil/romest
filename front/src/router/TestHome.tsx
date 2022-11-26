import React, {FC, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Button} from "@chakra-ui/react";
import {useAppSelector} from "../redux/reduxUtil";
import {getUserData, useSecureUserData} from "../redux/reducers/UserUtil";


export const TestHome: FC = () => {

    console.log("something")

    // const {email, roles} = useAppSelector(state => state.userReducer)
    const userState = useAppSelector(state => state.userReducer)
    const navigate = useNavigate()
    const userData = useSecureUserData()

    useEffect(() => {
        if (userData) {
            navigate("/home")
        }
    }, [])

    return (
        <>
            <h1>Test Home.</h1>
        </>
    )
}