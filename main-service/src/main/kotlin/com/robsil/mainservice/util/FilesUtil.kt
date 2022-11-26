package com.robsil.mainservice.util

import com.robsil.mainservice.model.exception.IllegalRequestPayloadException
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path

fun Path.createDirectoryIfExists(path: Path) {
    if (!Files.exists(path)) {
        Files.createDirectory(path)
    }
}

// this method supports only if extension includes only 1 dot.
fun MultipartFile.getExtensionIfSingleDot(): String {
    val fileName = this.originalFilename
        ?: throw IllegalRequestPayloadException("MultipartFile has null original file name.")

    val split = fileName.split(".")

    if (split.size != 2) {
        throw IllegalRequestPayloadException("MultipartFile has invalid original file name. More than 1 dot in name.")
    }

    return split[1]
}