package com.robsil.mainservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.robsil.mainservice.model.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
) {

    @ExceptionHandler(NotFoundException::class)
    fun onNotFoundException(e: NotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalRequestPayloadException::class)
    fun onIllegalRequestPayloadException(e: IllegalRequestPayloadException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun onInternalServerErrorException(e: InternalServerErrorException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun onForbiddenException(e: ForbiddenException): ResponseEntity<String> {
        return ResponseEntity(objectMapper.writeValueAsString(e), HttpStatus.valueOf(e.status))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun onUnauthorizedException(e: UnauthorizedException): ResponseEntity<String> {
        return ResponseEntity(objectMapper.writeValueAsString(e), HttpStatus.valueOf(e.status))
    }

    @ExceptionHandler(DataUniqueViolationException::class)
    fun onDataUniqueViolationException(e: DataUniqueViolationException): ResponseEntity<String> {
        return ResponseEntity(objectMapper.writeValueAsString(e), HttpStatus.valueOf(e.status))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<String> {
        val response = ValidationErrorResponse("Validation error.")

        e.bindingResult.fieldErrors.forEach { response.violations.add(Violation(it.field, it.defaultMessage)) }

        return ResponseEntity(objectMapper.writeValueAsString(response), HttpStatus.BAD_REQUEST)
    }

//    @ExceptionHandler(HttpMessageNotReadableException::class)
//    fun onHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<String> {
//        return ResponseEntity("custom", HttpStatus.BAD_REQUEST)
//    }
}