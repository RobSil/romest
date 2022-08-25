package com.robsil.model

import java.security.Principal

class UserPrincipal(
    private val name: String
) : Principal {

    override fun getName(): String {
        return name
    }

}