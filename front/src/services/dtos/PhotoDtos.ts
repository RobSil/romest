import {StoringSource} from "../enums/StoringSource";


export interface SimplePhotoDto {
    path: string

    storingSource: StoringSource

    imageKitId: string
    imageKitUrl: string

    photoBytes: Uint8Array
}
