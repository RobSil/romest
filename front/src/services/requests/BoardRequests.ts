import {UserSimpleDto} from "../dtos/UserDtos";


export interface BoardCreateRequest {
    name: string,
    isPrivate: boolean
}

export interface SimpleBoardDto {
    id: number
    name: string
    isPrivate: boolean
}

export interface BoardPickDto {
    id: number
    name: string
    isPrivate: boolean
}

export interface CompleteBoardDto {
    id: number
    name: string
    minimizedName: string
    isPrivate: boolean
    user: UserSimpleDto
}
