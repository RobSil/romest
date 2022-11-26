

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