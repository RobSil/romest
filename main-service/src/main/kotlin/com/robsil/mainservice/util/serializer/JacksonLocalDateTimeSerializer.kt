package com.robsil.mainservice.util.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//class JacksonLocalDateTimeSerializer(type: Class<LocalDateTime>) : StdSerializer<LocalDateTime>(type) {
//
//    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//
//    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, provider: SerializerProvider?) {
//        gen!!.writeString(formatter.format(value))
//    }
//}