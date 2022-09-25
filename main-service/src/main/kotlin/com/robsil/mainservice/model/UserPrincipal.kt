package com.robsil.mainservice.model

import java.security.Principal

class UserPrincipal(
    private val name: String
) : Principal {

    override fun getName(): String {
        return name
    }

}