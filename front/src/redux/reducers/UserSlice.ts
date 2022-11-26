import {createSlice, current, PayloadAction} from "@reduxjs/toolkit";
import {UserInformationDto} from "../../services/dtos/UserDtos";
import {clearUserData} from "./UserUtil";


export interface UserState {
    username: string,
    email: string | null,
    roles: string[],
    authenticated: boolean,
}

const initialState: UserState = {
    username: "",
    email: null,
    roles: [],
    authenticated: false
}

export const userSlice = createSlice({
    name: "user",
    initialState: initialState,
    reducers: {
        setUserInfo(state, action: PayloadAction<UserInformationDto>) {
            console.log(current(state))
            // state.email = action.payload.email
            // state.roles = action.payload.roles
            // state.authenticated = true
            state = {...action.payload, authenticated: true}
            localStorage.setItem("userData", JSON.stringify(state))
            return {...action.payload, authenticated: true}
        },
        authenticate(state, action: PayloadAction<string>) {
            state.email = "Loading.."
            state.roles = []
            state.authenticated = true
        },
        unauthorized(state, action: PayloadAction<string>) {
            console.log("logout trigger")
            clearUserData()
            state.email = ""
            state.roles = []
            state.authenticated = false
        }
    },

})

export default userSlice.reducer