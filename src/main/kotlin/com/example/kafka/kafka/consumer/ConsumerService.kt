package com.example.kafka.kafka.consumer

import com.example.kafka.events.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.*


@Service
class ConsumerService(@Autowired val mapper: ObjectMapper) {
    val logger = LoggerFactory.getLogger(ConsumerService::class.java)

    @KafkaListener(topics = ["\${topic}"], groupId = "G1", batch = "true")
    fun batch(records: ConsumerRecords<Int, User>) {
        records.forEach { record ->
            val map = HashMap<String, Any>()
            map["message"] = record.toString()
            map["createdAt"] = Date()
            logger.error(mapper.writeValueAsString(map))
        }
    }

//    @KafkaListener(topics = ["\${topic}"], groupId = "G1")
//    fun batch(record: ConsumerRecord<Int, User>) {
//        val map = HashMap<String, Any>()
//        map["message"] = record
//        map["createdAt"] = Date()
//        logger.error(mapper.writeValueAsString(map))
//    }
}