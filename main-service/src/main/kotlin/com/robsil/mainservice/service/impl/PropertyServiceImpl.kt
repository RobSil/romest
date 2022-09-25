package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Property
import com.robsil.mainservice.data.repository.PropertyRepository
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.PropertyService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
@Transactional
class PropertyServiceImpl(
    private val propertyRepository: PropertyRepository
) : PropertyService {

    private val log = logger()

    override fun getByName(name: String): Property {
        return propertyRepository.findByName(name) ?: run {
            log.info("propertyService getByName: property is null. Name: $name")
            throw NotFoundException("PROPERTY_NOT_FOUND")
        }
    }

    override fun getByName(name: String, defaultValue: String): Property {
        return propertyRepository.findByName(name) ?: Property(name, defaultValue)
    }

    override fun getValueByName(name: String): String {
        return propertyRepository.findByName(name)?.value ?: run {
            log.info("propertyService getByName: property is null. Name: $name")
            throw NotFoundException("PROPERTY_NOT_FOUND")
        }
    }

    override fun getValueByName(name: String, defaultValue: String): String {
        return propertyRepository.findByName(name)?.value ?: defaultValue
    }

    @Transactional
    override fun set(name: String, value: String): Property {
        var property: Property

        try {
            property = getByName(name)
            property.value = "as"
        } catch (ex: NotFoundException) {
            property = Property(name, value)
        }

//        if (property == null) {
//            log.error("propertyService set: somehow unreachable statement occurred. Name: $name, value: $value")
//            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)
//        }

        return property
    }
}