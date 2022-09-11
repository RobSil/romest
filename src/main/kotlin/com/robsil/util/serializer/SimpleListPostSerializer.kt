package com.robsil.util.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.robsil.data.domain.Post
import com.robsil.model.dto.SimplePostDto


class SimpleListPostSerializer : JsonSerializer<List<Post>>() {
    override fun serialize(value: List<Post>?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (value == null) {
            return
        }
//        gen!!.writeStartArray()
//        gen.writeObjectField("posts", value.map {
//            gen.writeNumberField("id", it.id ?: -1L)
//            gen.writeStringField("title", it.title)
//            gen.writeStringField("text", it.text)
//        })
//        gen!!.writeStartObject()

        val valueToWrite = value.map { SimplePostDto(it.id, it.title, it.text) }

        gen!!.writeStartObject()
        gen.writeObjectField("posts", valueToWrite)
        gen.writeEndObject()

//        gen!!.writeStartArray()
//        value.forEach {
//            gen.writeNumberField("id", it.id ?: -1L)
//            gen.writeStringField("title", it.title)
//            gen.writeStringField("text", it.text)
//        }
//        gen.writeEndArray()

//        gen.writeEndObject()
//        gen.writeEndArray()
    }

}