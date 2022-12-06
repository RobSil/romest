package com.robsil.mainservice.model.dto

import com.robsil.mainservice.model.enum.StoringSource

data class SimplePhotoDto(
    val path: String,

    val storingSource: StoringSource,

    val imageKitId: String = "",
    val imageKitUrl: String = "",

    val photoBytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimplePhotoDto

        if (path != other.path) return false
        if (storingSource != other.storingSource) return false
        if (imageKitId != other.imageKitId) return false
        if (imageKitUrl != other.imageKitUrl) return false
        if (!photoBytes.contentEquals(other.photoBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + storingSource.hashCode()
        result = 31 * result + imageKitId.hashCode()
        result = 31 * result + imageKitUrl.hashCode()
        result = 31 * result + photoBytes.contentHashCode()
        return result
    }
}
