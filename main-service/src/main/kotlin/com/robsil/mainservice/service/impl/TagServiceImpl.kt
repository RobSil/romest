package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Tag
import com.robsil.mainservice.data.repository.TagRepository
import com.robsil.mainservice.model.dto.request.TagCreateRequest
import com.robsil.mainservice.model.exception.DataUniqueViolationException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.TagService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class TagServiceImpl(
    private val tagRepository: TagRepository,
) : TagService {

    override fun getAllIdsByPostIds(postIds: List<Long>): List<Long> {
        return tagRepository.findAllIdsByPostIds(postIds)
    }

    override fun getByTitle(title: String): Tag {
        return tagRepository.findByTitle(title)
            ?: run {
//                log.info("userService getByEmail: user not found. Email: $email")
                throw NotFoundException("TAG_NOT_FOUND")
            }
    }

    fun saveEntity(tag: Tag): Tag {
        return tagRepository.save(tag)
    }

    override fun create(dto: TagCreateRequest): Tag {
        val tag = Tag(dto.title)

        try {
            return saveEntity(tag)
        } catch (e: DataIntegrityViolationException) {
            if (e.message?.contains("duplicate") == true) {
                throw DataUniqueViolationException("During saving an tag, unique constraint violation occurred.", e)
            }

            throw e
        }
    }
}