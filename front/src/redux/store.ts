import {combineReducers, configureStore} from "@reduxjs/toolkit";
import userReducer from "./reducers/UserSlice"


// const rootReducer = combineReducers({
//     userReducer
// })
//
// export const store = () => configureStore(
//     {
//         reducer: rootReducer
//     }
// )

export const store = configureStore(
    {
        reducer: {
            userReducer
        }
    }
)

// export type RootState = ReturnType<typeof rootReducer>
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
// export type AppStore = ReturnType<typeof store>
// export type AppDispatch = AppStore["dispatch"]
