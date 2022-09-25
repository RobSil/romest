package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Role
import com.robsil.mainservice.model.enum.ERole

interface RoleService {

    fun getById(id: Long): Role

    fun getByName(title: String): Role

    fun getByName(eRole: ERole): Role

    fun create(title: String): Role

}