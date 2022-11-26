package com.robsil.mainservice.model.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TagCreateRequest(

    @field:NotBlank
    @field:Length(min = 1, max = 32)
    val title: String

)