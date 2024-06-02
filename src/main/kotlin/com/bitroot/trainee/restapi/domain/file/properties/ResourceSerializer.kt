package com.bitroot.trainee.restapi.domain.file.properties

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.core.io.Resource
import org.springframework.util.StreamUtils
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class ResourceSerializer : JsonSerializer<Resource>() {
    @Throws(IOException::class)
    override fun serialize(
        resource: Resource,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider,
    ) {
        jsonGenerator.writeStartObject()
        jsonGenerator.writeStringField("filename", resource.filename ?: "")

        val fileData = Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(resource.inputStream))
        jsonGenerator.writeStringField("fileData", fileData)

        // You can add more fields if needed
        jsonGenerator.writeEndObject()
    }
}

