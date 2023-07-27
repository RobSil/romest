import {BoardCreateRequest, BoardPickDto, CompleteBoardDto, SimpleBoardDto} from "./requests/BoardRequests";
import $api from "./axios";
import {AxiosResponse} from "axios";


export const boardApiPrefix = "/api/v1/boards"
export const userApiPrefix = "/api/v1/users"

export default class BoardService {

    static async getByUsernameAndMinimizedName(username: string, minimizedName: string): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.get(boardApiPrefix + "/byUsernameAndMinimizedName", { params: { username, minimizedName }})
    }

    static async getAllByUser(username: string): Promise<AxiosResponse<SimpleBoardDto[]>> {
        return $api.get(boardApiPrefix + `/byUsername`, { params: {username} })
    }

    static async getAllForPick(): Promise<AxiosResponse<BoardPickDto[]>> {
        return $api.get(boardApiPrefix + "/pick")
    }

    static async create(dto: BoardCreateRequest): Promise<AxiosResponse<CompleteBoardDto>> {
        return $api.post(boardApiPrefix, dto)
    }
}
