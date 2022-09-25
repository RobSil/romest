package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Property

interface PropertyService {

    fun getByName(name: String): Property

    fun getByName(name: String, defaultValue: String): Property

    fun getValueByName(name: String): String

    fun getValueByName(name: String, defaultValue: String): String

    fun set(name: String, value: String): Property
}