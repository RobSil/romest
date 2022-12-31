import $api from "./axios";
import {AxiosResponse} from "axios";
import {
    ComplexPostDto,
    ComplexPostPageableDto,
    LikeResponse,
    PostCreateDto,
    SimplePostDto
} from "./requests/PostRequest";
import {boardApiPrefix} from "./BoardService";

export const postApiPrefix = "/api/v1/posts"

export default class PostService {
    static async getByUsernameAndBoardMinimizedName(username: string, minimizedName: string, pageNumber: number, pageSize: number): Promise<AxiosResponse<ComplexPostPageableDto>> {
        return $api.get(boardApiPrefix + "/byUsernameAndMinimizedName/posts", {
            params: {
                username,
                minimizedName,
                pageNumber,
                pageSize
            }
        })
    }

    static async getById(id: number): Promise<AxiosResponse<ComplexPostDto>> {
        return $api.get(postApiPrefix + "/" + id.toString())
    }

    static async repin(postId: number, boardId: number): Promise<AxiosResponse<SimplePostDto>> {
        return $api.post(postApiPrefix + "/" + postId.toString() + "/repin", null, { params: {boardId}})
    }

    static async toggleLike(postId: number): Promise<AxiosResponse<LikeResponse>> {
        return $api.post(postApiPrefix + "/" + postId.toString() + "/toggleLike")
    }

    // static async like(id: number): Promise<AxiosResponse<ComplexPostDto>> {
    //     return
    // }

    static async create(dto: PostCreateDto, image: File) {
        const formData = new FormData()

        const dtoBlob = new Blob([JSON.stringify(dto)], {type: "application/json"})

        formData.set("dto", dtoBlob)
        formData.set("file", image, image.name)

        return $api.post(postApiPrefix, formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        })
    }
}
