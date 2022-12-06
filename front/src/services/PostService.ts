import $api from "./axios";
import {AxiosResponse} from "axios";
import {ComplexPostPageableDto} from "./requests/PostRequest";

export default class PostService {
    static async getByUsernameAndBoardMinimizedName(username: string, minimizedName: string, pageNumber: number, pageSize: number): Promise<AxiosResponse<ComplexPostPageableDto>> {
        return $api.get("/api/v1/boards/byUsernameAndMinimizedName/posts", {
            params: {
                username,
                minimizedName,
                pageNumber,
                pageSize
            }
        })
    }
}
