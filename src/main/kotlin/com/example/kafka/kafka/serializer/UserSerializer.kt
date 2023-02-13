package com.example.kafka.kafka.serializer

import com.example.kafka.events.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.kafka.common.serialization.Serializer
import org.springframework.stereotype.Component

@Component
class UserSerializer() : Serializer<User> {
    companion object {
        val objectMapper: ObjectMapper = ObjectMapper().apply {
            registerModule(KotlinModule())
        }
    }

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun serialize(topic: String?, data: User?): ByteArray {
        return objectMapper.writeValueAsBytes(data)
    }

    override fun close() {}
}