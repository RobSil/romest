import {BoardCreateRequest} from "./requests/BoardRequests";
import $api from "./axios";


export default class BoardService {

    static async getByUsernameAndMinimizedName(username: String, minimizedName: String) {
        return $api.get("/api/v1/boards/byUsernameAndMinimizedName", { params: { username, minimizedName }})
    }

    static async create(dto: BoardCreateRequest) {
        return $api.post("/api/v1/boards", dto)
    }
}