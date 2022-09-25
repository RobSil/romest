package com.robsil.mainservice.service

import com.robsil.mainservice.model.enum.FileType
import com.robsil.mainservice.model.image.ImageSaveResult
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileService {

//    private fun save(path: Path, multipartFile: MultipartFile): File

    fun saveAvatar(folderPath: String, fileName: String, fileType: FileType, multipartFile: MultipartFile): File

    fun savePhoto(folderPath: String, fileName: String, multipartFile: MultipartFile): ImageSaveResult
}