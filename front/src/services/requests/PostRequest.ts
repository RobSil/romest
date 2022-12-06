import {SimplePhotoDto} from "../dtos/PhotoDtos";


export interface PostCreateDto {
    boardId: number,
    title: string,
    text: string,
    tags: Set<string>,
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

export interface ComplexPostDto {
    id: number
    title: string
    text: string
    photo: SimplePhotoDto
}

