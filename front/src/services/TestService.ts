import $api from "./axios";
import {useAppDispatch} from "../redux/reduxUtil";
import {userSlice} from "../redux/reducers/UserSlice";

export default class TestService {

    // we actually need here only the status code.
    // 401 -> unauthenticated (unauthorized)
    // 200 -> successfully authenticated
    static async fetchPosts(): Promise<any> {
        let promise = $api.post("/login")

        // promise.then((req) => {
        //     if (req.request.status === 200) {
        //         dispatch(userSlice.actions.setUserInfo({}))
        //     }
        // })

    }

}