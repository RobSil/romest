import {createAsyncThunk} from "@reduxjs/toolkit";
import AuthService from "../../services/AuthService";
import {AppDispatch} from "../store";
import {userSlice} from "./UserSlice";
import {AxiosResponse} from "axios";
import {EmailPasswordPair, UserInformationDto} from "../../services/dtos/UserDtos";


export const login = (email: string, password: string) => async (dispatch: AppDispatch): Promise<AxiosResponse<UserInformationDto>> => {
    const response = AuthService.login(email, password)

    response.then((req) => {
        if (req.request.status === 200) {
            dispatch(userSlice.actions.setUserInfo(req.data))
        }
    })

    return response
}

// actually doesn't return, cause of document.location
export const logout = () => async (dispatch: AppDispatch) => {
    const response = await AuthService.logout()
    dispatch(userSlice.actions.unauthorized("unauthorized"))
    document.location = "/login"

    return response
}

// export const login = createAsyncThunk<UserInformationDto, any>(
//     "user/login",
//     async ({name: string}, { rejectWithValue }) => {
//         try {
//             return {}
//         }
//     }
// )

// export const login = createAsyncThunk<AxiosResponse<UserInformationDto>, EmailPasswordPair, {rejectValue: AxiosResponse}>(
//     'user/login',
//     async ({ email, password }, thunkAPI) => {
//         const response = await AuthService.login(email, password)
//         const data = await response.data
//
//         if (response.request.status === 200) {
//             return data
//         }
//
//         thunkAPI.rejectWithValue(response)
//     }
// )