package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Role
import com.robsil.mainservice.data.repository.RoleRepository
import com.robsil.mainservice.model.enum.ERole
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.RoleService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RoleServiceImpl(
    private val roleRepository: RoleRepository
): RoleService {

    private val log = logger()

    override fun getById(id: Long): Role {
        return roleRepository.findById(id).orElse(null) ?: run {
            log.info("getById: can't find role. ID: $id")
            throw NotFoundException("ROLE_NOT_FOUND")
        }
    }

    override fun getByName(title: String): Role {
        return roleRepository.findByTitle(title) ?: run {
            log.info("getById: can't find role. Name: $title")
            throw NotFoundException("ROLE_NOT_FOUND")
        }
    }

    override fun getByName(eRole: ERole): Role {
        return roleRepository.findByTitle(eRole.toString()) ?: run {
            log.info("getById: can't find role. Name: ${eRole.toString()}")
            throw NotFoundException("ROLE_NOT_FOUND")
        }
    }

    fun saveEntity(role: Role): Role {
        return roleRepository.save(role)
    }

    @Transactional
    override fun create(title: String): Role {
        return saveEntity(Role(title))
    }
}