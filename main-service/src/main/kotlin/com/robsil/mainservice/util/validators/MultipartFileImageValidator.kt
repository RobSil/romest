package com.robsil.mainservice.util.validators

import com.robsil.mainservice.util.getExtensionIfSingleDot
import com.robsil.mainservice.util.validators.annotations.MultipartFileImageConstraint
import org.springframework.http.MediaType
import org.springframework.util.MimeType
import org.springframework.web.multipart.MultipartFile
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class MultipartFileImageValidator: ConstraintValidator<MultipartFileImageConstraint, MultipartFile> {
    override fun isValid(value: MultipartFile?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }

        if (!MultipartFileImageValidatorConstants.IMAGE_TYPES.contains(value.getExtensionIfSingleDot())) {
            return false
        }

        return true
    }
}

object MultipartFileImageValidatorConstants {
    val IMAGE_TYPES: List<String> = listOf(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE)
}