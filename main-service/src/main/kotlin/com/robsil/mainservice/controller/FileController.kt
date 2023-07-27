package com.robsil.mainservice.controller

import com.robsil.mainservice.service.FileService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/files")
class FileController(
    private val fileService: FileService
) {

}
