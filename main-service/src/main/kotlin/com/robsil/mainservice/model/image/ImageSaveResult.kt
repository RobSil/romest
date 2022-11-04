package com.robsil.mainservice.model.image

import com.robsil.mainservice.model.enum.StoringSource

data class ImageSaveResult(
    val fileId: String,
    val path: String,
    val storingSource: StoringSource,
    val imageKitId : String,
    val imageKitUrl: String,
)
