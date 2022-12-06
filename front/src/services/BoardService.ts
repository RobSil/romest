import {BoardCreateRequest, CompleteBoardDto} from "./requests/BoardRequests";
import $api from "./axios";
import {AxiosResponse} from "axios";


export default class BoardService {

    static async getByUsernameAndMinimizedName(username: String, minimizedName: String): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.get("/api/v1/boards/byUsernameAndMinimizedName", { params: { username, minimizedName }})
    }

    static async create(dto: BoardCreateRequest): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.post("/api/v1/boards", dto)
    }
}
