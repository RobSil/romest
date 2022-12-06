package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.model.dto.SimplePhotoDto
import com.robsil.mainservice.model.enum.FileType
import com.robsil.mainservice.model.enum.StoringSource
import com.robsil.mainservice.model.image.ImageSaveResult
import com.robsil.mainservice.service.FileService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Service
@Profile("fileSystemFileService")
class FileSystemFileService(
    @Value("\${data.image.avatar.path}")
    private val avatarPath: String,

    @Value("\${data.image.photo.path}")
    private val photoPath: String
) : FileService {

    private val log = logger()

    override fun getPhotoSource(photo: Photo): SimplePhotoDto {
        if (photo.storingSource != StoringSource.FILE_SYSTEM)
            throw IllegalArgumentException("Storing source should be imageKit to delete it here.")

        val file = Files.readAllBytes(Path.of(photo.path))

        return SimplePhotoDto(photo.path, photo.storingSource, "", "", file)
    }

    private fun save(path: Path, multipartFile: MultipartFile): File {
        val createdFile: Path = Files.createFile(path)

        val file = File(createdFile.toString())

        file.writeBytes(multipartFile.bytes)

//            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "somehow got unknown fileType, can't map path.")

        return file
    }

    override fun saveAvatar(folderPath: String, fileName: String, fileType: FileType, multipartFile: MultipartFile): File {
        return save(Path.of("$avatarPath/$folderPath/$fileName.jpg"), multipartFile)
    }

    override fun savePhoto(folderPath: String, fileName: String, multipartFile: MultipartFile): ImageSaveResult {
        val fullPath = "$photoPath/$folderPath/$fileName.jpg"

        val file = save(Path.of(fullPath), multipartFile)

        return ImageSaveResult(fileName, fullPath, StoringSource.FILE_SYSTEM, "", "")
    }

    override fun delete(photo: Photo) {
        if (photo.storingSource != StoringSource.FILE_SYSTEM)
            throw IllegalArgumentException("Storing source should be fileSystem to delete it here.")

        Files.deleteIfExists(Path.of(photo.path))
        TODO("Not yet implemented")
    }
}
