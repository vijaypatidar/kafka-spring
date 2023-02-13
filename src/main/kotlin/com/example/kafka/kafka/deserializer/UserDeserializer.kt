package com.example.kafka.kafka.deserializer

import com.example.kafka.events.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer

class UserDeserializer() : Deserializer<User> {
    companion object {
        val objectMapper: ObjectMapper = ObjectMapper().apply {
            registerModule(KotlinModule())
        }
    }

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun deserialize(topic: String, data: ByteArray): User? {
        return try {
            objectMapper.readValue(data, User::class.java)
        } catch (e: Exception) {
            throw SerializationException("Error when deserializing byte[] to Model class")
        }
    }

    override fun close() {}
}