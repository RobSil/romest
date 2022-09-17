package com.robsil.service.impl.facade

import com.robsil.data.domain.Photo
import com.robsil.data.domain.Post
import com.robsil.service.FileService
import com.robsil.service.PhotoService
import com.robsil.service.facade.PhotoServiceFacade
import com.robsil.util.getRandomString
import com.robsil.util.minimize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class PhotoServiceFacade(
    private val photoService: PhotoService,
    private val fileService: FileService
) : PhotoServiceFacade {

    val stringLength = 10

    @Transactional
    override fun save(post: Post, minimizedBoardName: String, multipartFile: MultipartFile): Photo {
        val fileName = minimizedBoardName + "/" + getRandomString(stringLength)

        val file: File = fileService.savePhoto(fileName, multipartFile)

        var photo = Photo(file.path, post)
        photo = photoService.saveEntity(photo)

        return photo
    }

}