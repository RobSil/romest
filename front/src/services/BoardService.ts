import {BoardCreateRequest, BoardPickDto, CompleteBoardDto} from "./requests/BoardRequests";
import $api from "./axios";
import {AxiosResponse} from "axios";


export const boardApiPrefix = "/api/v1/boards"

export default class BoardService {

    static async getByUsernameAndMinimizedName(username: String, minimizedName: String): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.get(boardApiPrefix + "/byUsernameAndMinimizedName", { params: { username, minimizedName }})
    }

    static async getAllForPick(): Promise<AxiosResponse<BoardPickDto[]>> {
        return $api.get(boardApiPrefix + "/pick")
    }

    static async create(dto: BoardCreateRequest): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.post(boardApiPrefix, dto)
    }
}
