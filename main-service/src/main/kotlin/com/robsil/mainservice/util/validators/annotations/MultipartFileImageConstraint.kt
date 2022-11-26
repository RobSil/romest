package com.robsil.mainservice.util.validators.annotations

import com.robsil.mainservice.util.validators.MultipartFileImageValidator
import javax.validation.Constraint

@MustBeDocumented
@Constraint(validatedBy = [MultipartFileImageValidator::class])
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MultipartFileImageConstraint() {

}
