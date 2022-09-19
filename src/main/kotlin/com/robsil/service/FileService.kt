package com.robsil.service

import com.robsil.model.enum.FileType
import com.robsil.model.image.ImageSaveResult
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path

interface FileService {

//    private fun save(path: Path, multipartFile: MultipartFile): File

    fun saveAvatar(folderPath: String, fileName: String, fileType: FileType, multipartFile: MultipartFile): File

    fun savePhoto(folderPath: String, fileName: String, multipartFile: MultipartFile): ImageSaveResult
}