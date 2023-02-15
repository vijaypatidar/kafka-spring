package com.example.kafka.consumers

import com.example.kafka.events.UserCreateFailedEvent
import com.example.kafka.events.UserCreateStatus
import com.example.kafka.mappers.DataMapperProvider
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ConsumerService(
    @Autowired val template: KafkaTemplate<Int, GenericRecord>,
    @Autowired val dataMapperProvider: DataMapperProvider
) {
    val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

    @KafkaListener(
        topics = ["\${topic}"],
        groupId = "\${spring.kafka.properties.group.id}",
        batch = "true"
    )
    fun batch(records: ConsumerRecords<Int, UserCreateStatus>) {
        records.forEach { record ->
            process(record)
        }
    }

    private fun process(record: ConsumerRecord<Int, UserCreateStatus>) {
        logger.info("Transforming UserCreateStatus: ${record.value()}")
        val transformer = dataMapperProvider.get(record.value().getStatus())
        val transformed = transformer.transform(record.value())
        template.send(getTopicName(transformed), transformed)
    }

    private fun getTopicName(record: GenericRecord): String {
        return if (record is UserCreateFailedEvent) {
            "failed"
        } else {
            "success"
        }
    }

//    @KafkaListener(topics = ["\${topic}"], groupId = "G1")
//    fun batch(record: ConsumerRecord<Int, UserCreateStatus>) {
//        val map = HashMap<String, Any>()
//        map["message"] = record
//        map["createdAt"] = Date()
//        logger.error(mapper.writeValueAsString(map))
//    }
}