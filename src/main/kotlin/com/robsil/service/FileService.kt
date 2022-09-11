package com.robsil.service

import com.robsil.model.enum.FileType
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path

interface FileService {

//    private fun save(path: Path, multipartFile: MultipartFile): File

    fun saveAvatar(fileName: String, fileType: FileType, multipartFile: MultipartFile): File

    fun savePhoto(fileName: String, multipartFile: MultipartFile): File
}