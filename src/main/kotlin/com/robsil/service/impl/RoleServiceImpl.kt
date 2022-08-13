package com.robsil.service.impl

import com.robsil.data.domain.Role
import com.robsil.data.repository.RoleRepository
import com.robsil.model.enum.ERole
import com.robsil.model.exception.NotFoundException
import com.robsil.service.RoleService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
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

    @Transactional
    override fun create(title: String): Role {
        return roleRepository.save(Role(title))
    }
}