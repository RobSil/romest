import {SimplePhotoDto} from "../dtos/PhotoDtos";
import {ComplexUserDto} from "../dtos/UserDtos";


export interface PostCreateDto {
    boardId: number,
    title: string | null,
    text: string | null,
    tags: string[],
}

export interface PostCreateRequest {
    dto: PostCreateDto,
    file: File
}

export interface ComplexPostPageableDto {
    posts: ComplexPostDto[]
    totalElements: number
    totalPages: number
}

export interface SimplePostDto {
    id: number
    title: string
    text: string
}

export interface ComplexPostDto {
    id: number
    title: string
    text: string
    isLiked: boolean
    photo: SimplePhotoDto,
    author: ComplexUserDto,
}

export interface LikeResponse {
    isLiked: boolean
}
