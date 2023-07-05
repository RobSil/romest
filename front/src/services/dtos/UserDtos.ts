import {SimplePhotoDto} from "./PhotoDtos";


export interface UserInformationDto {
    id: number
    username: string,
    email: string
    roles: string[]
}

export interface UserSimpleDto {
    username: string
    email: string
}

export interface EmailPasswordPair {
    email: string,
    password: string
}
export interface UserRegistrationRequest {
    username: string
    email: string,
    password: string,
}

export interface ComplexUserDto {
    username: String
    avatar: SimplePhotoDto
}
