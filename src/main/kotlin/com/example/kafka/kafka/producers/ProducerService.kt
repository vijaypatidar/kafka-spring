package com.example.kafka.kafka.producers

import com.example.kafka.events.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
class ProducerService(@Autowired val targetTopic: KafkaTemplate<String, User>) {

    @Scheduled(fixedDelay = 3000)
    fun test() {
        val hobbit = User.newBuilder()
            .setName("Vijay")
            .build()
        targetTopic.send("source", hobbit)
    }
}