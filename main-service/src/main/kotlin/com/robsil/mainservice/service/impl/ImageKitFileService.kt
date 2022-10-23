package com.robsil.mainservice.service.impl

import com.robsil.mainservice.model.enum.FileType
import com.robsil.mainservice.model.enum.StoringSource
import com.robsil.mainservice.model.image.ImageSaveResult
import com.robsil.mainservice.service.FileService
import io.imagekit.sdk.ImageKit
import io.imagekit.sdk.models.FileCreateRequest
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
@Profile("imageKitFileService")
class ImageKitFileService(
    @Value("\${data.image.avatar.path}")
    private val avatarPath: String,

    @Value("\${data.image.photo.path}")
    private val photoPath: String,

    private val imageKit: ImageKit,

    ): FileService {

    val log = logger()

    override fun saveAvatar(folderPath: String, fileName: String, fileType: FileType, multipartFile: MultipartFile): File {
        TODO("Not yet implemented")
    }

    override fun savePhoto(folderPath: String, fileName: String, multipartFile: MultipartFile): ImageSaveResult {
        val request = FileCreateRequest(multipartFile.bytes, fileName)

        request.setFolder(folderPath)
        request.setPrivateFile(false)
        request.setTags(listOf("photo"))

        val result = imageKit.upload(request)
            ?: throw RuntimeException("Something went wrong during uploading photo, result is null")

//        return ImageSaveResult(result.fileId, result.filePath, StoringSource.IMAGEKIT)
        return ImageSaveResult(result.fileId, result.fileId, StoringSource.IMAGEKIT)
    }

    override fun delete(path: String) {

        imageKit.deleteFile(path)
        TODO("Not yet implemented")
    }
}