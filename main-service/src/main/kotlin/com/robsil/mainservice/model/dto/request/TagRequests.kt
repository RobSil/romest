package com.robsil.mainservice.model.dto.request

import org.hibernate.validator.constraints.Length
import jakarta.validation.constraints.NotBlank

data class TagCreateRequest(

    @field:NotBlank
    @field:Length(min = 1, max = 32)
    val title: String

)
