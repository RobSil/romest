package com.robsil.controller

import com.robsil.service.FileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@RestController
@RequestMapping("/api/v1/files")
class FileController(
    private val fileService: FileService
) {
//    @GetMapping("/test")
//    fun testFileSystem(multipartFile: MultipartFile) {
//        fileService.save(Path.of(""))
//    }
}