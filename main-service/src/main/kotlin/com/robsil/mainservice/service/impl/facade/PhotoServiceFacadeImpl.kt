package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.service.FileService
import com.robsil.mainservice.service.PhotoService
import com.robsil.mainservice.service.facade.PhotoServiceFacade
import com.robsil.mainservice.util.getRandomString
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