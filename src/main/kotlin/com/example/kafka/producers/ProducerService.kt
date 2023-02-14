package com.example.kafka.producers

import com.example.kafka.events.UserCreateStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random


@Service
class ProducerService(@Autowired val targetTopic: KafkaTemplate<Int, UserCreateStatus>) {

    val faker = Random(200)

    @Scheduled(fixedDelay = 5000)
    fun test() {
        val hobbit = UserCreateStatus.newBuilder()
            .setName("Vijay")
            .setRequestId(UUID.randomUUID().toString())
            .setStatus(
                if (faker.nextBoolean())
                    "success"
                else
                    "failed"
            )
            .build()

        targetTopic.send("source", faker.nextInt(40), hobbit)
    }
}