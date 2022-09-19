package com.robsil.model.image

import com.robsil.model.enum.StoringSource

data class ImageSaveResult(
    val fileId: String,
    val path: String,
    val storingSource: StoringSource,
)
