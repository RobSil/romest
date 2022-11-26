import {store} from "../store";
import {UserInformationDto} from "../../services/dtos/UserDtos";
import AuthService from "../../services/AuthService";
import {userSlice, UserState} from "./UserSlice";

import {FC, useEffect} from "react";
import {useAppDispatch, useAppSelector} from "../reduxUtil";

export const getUserData = (): UserInformationDto | null => {
    const userData = localStorage.getItem("userData")

    if (userData === null) {
        return null
    }

    return JSON.parse(userData) as UserInformationDto
}

export const useUserData = (): UserInformationDto | null => {
    const userState: UserState = useAppSelector(state => state.userReducer)

    useEffect(() => {

    }, [userState])

    if (userState != null) {
        const userData = localStorage.getItem("userData")

        if (userData === null) {
            return null
        }

        return JSON.parse(userData) as UserInformationDto
    } else {
        const userData = localStorage.getItem("userData")

        if (userData === null) {
            return null
        }

        return JSON.parse(userData) as UserInformationDto
    }
}

export const useSecureUserData = (): UserInformationDto | null => {
    const dispatch = useAppDispatch()
    const userData = getUserData()

    if (userData == null) {
        AuthService.refresh()
            .then((req) => {
                if (req.request.status === 200) {
                    // store.dispatch(userSlice.actions.setUserInfo(req.data))
                    return getUserData()
                } else {
                    // store.dispatch(userSlice.actions.unauthorized("unauthorized"))
                    dispatch(userSlice.actions.unauthorized("unauthorized"))
                    document.location = "/login"
                }
            })
    }

    return userData
}

export const clearUserData = () => {
    localStorage.removeItem("userData")
}