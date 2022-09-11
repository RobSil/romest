package com.robsil.service.impl

import com.robsil.model.enum.FileType
import com.robsil.service.FileService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Service
class FileSystemFileService(
    @Value("\${data.image.avatar.path}")
    private val avatarPath: String,

    @Value("\${data.image.photo.path}")
    private val photoPath: String
) : FileService {

    private val log = logger()

    private fun save(path: Path, multipartFile: MultipartFile): File {
        val createdFile: Path = Files.createFile(path)

        val file = File(createdFile.toString())

        file.writeBytes(multipartFile.bytes)

//            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "somehow got unknown fileType, can't map path.")

        return file
    }

    override fun saveAvatar(fileName: String, fileType: FileType, multipartFile: MultipartFile): File {
        return save(Path.of("$avatarPath/$fileName"), multipartFile)
    }

    override fun savePhoto(fileName: String, multipartFile: MultipartFile): File {
        return save(Path.of("$photoPath/$fileName.jpg"), multipartFile)
    }
}