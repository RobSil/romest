import $api from "./axios";
import {UserInformationDto, UserRegistrationRequest} from "./dtos/UserDtos";
import {AxiosResponse} from "axios";


import {FC} from "react";

// const AuthService: FC = () => {
//
//     const dispatch = useAppDispatch()
//
//     const login = (email: string, password: string): Promise<AxiosResponse<UserInformationDto>> => {
//         const promise = $api.post("/api/login", null, {params: {username: email, password: password}})
//
//         promise.then((req) => {
//             if (req.request.status === 200) {
//
//             }
//         })
//
//         return promise
//     }
//
//     const logout = (): Promise<any> => {
//         return $api.get("/api/logout")
//     }
// }

// export default AuthService

export default class AuthService {

    // we actually need here only the status code.
    // 401 -> unauthenticated (unauthorized)
    // 200 -> successfully authenticated
    static async login(email: string, password: string): Promise<AxiosResponse<UserInformationDto>> {
        return $api.post("/api/login", null, {params: {username: email, password: password}})
    }

    static async logout(): Promise<any> {
        return $api.get("/api/logout")
    }

    static async register(dto: UserRegistrationRequest) {
        return $api.post("/api/v1/users/register", {username: dto.username, email: dto.email, password: dto.password})
    }

    static async refresh(): Promise<AxiosResponse<UserInformationDto>> {
        return $api.get("/api/v1/users/refresh")
    }

}
