package com.robsil.service.impl.facade

import com.robsil.data.domain.Photo
import com.robsil.data.domain.Post
import com.robsil.service.FileService
import com.robsil.service.PhotoService
import com.robsil.service.facade.PhotoServiceFacade
import com.robsil.util.getRandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class PhotoServiceFacadeImpl(
    private val photoService: PhotoService,
    private val fileService: FileService
) : PhotoServiceFacade {

    val stringLength = 10

    override fun save(post: Post, minimizedBoardName: String, multipartFile: MultipartFile): Photo {
        val imageSaveResult = fileService.savePhoto(minimizedBoardName, getRandomString(stringLength), multipartFile)

        var photo = Photo(imageSaveResult.path, imageSaveResult.storingSource, post)
        photo = photoService.saveEntity(photo)

        return photo
    }

}