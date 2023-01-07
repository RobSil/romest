import axios, {AxiosError} from 'axios'
import {userSlice} from "../../redux/reducers/UserSlice";
import {store} from "../../redux/store";

const $api = axios.create({
    baseURL: "http://localhost:8081",
    withCredentials: true,
})

$api.interceptors.response.use((response) => {
    return response
}, (error) => {
    error = error as AxiosError
    // useNavigate()("/login?showToast=true")
    if (error.request.status == 401) {
        store.dispatch(userSlice.actions.unauthorized("unauthorized"))
    }
    // let dispatch = useAppDispatch();
    // dispatch(userSlice.actions.unauthorized("unauthorized"))
    // console.log(error.request.status)
    return error
})

export default $api
