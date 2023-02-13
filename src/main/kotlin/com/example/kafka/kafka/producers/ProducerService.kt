package com.example.kafka.kafka.producers

import com.example.kafka.events.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProducerService(@Autowired val targetTopic: KafkaTemplate<String, User>) {

    @Scheduled(fixedDelay = 3)
    fun test() {
        targetTopic.send("source", User("VIJAY" + Date().toString()))
    }
}