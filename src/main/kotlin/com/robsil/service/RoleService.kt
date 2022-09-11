package com.robsil.service

import com.robsil.data.domain.Role
import com.robsil.model.enum.ERole

interface RoleService {

    fun getById(id: Long): Role

    fun getByName(title: String): Role

    fun getByName(eRole: ERole): Role

    fun create(title: String): Role

}