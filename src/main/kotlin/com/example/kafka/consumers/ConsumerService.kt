package com.example.kafka.consumers

import com.example.kafka.events.UserCreateStatus
import com.example.kafka.transformers.FailTransformer
import com.example.kafka.transformers.SuccessTransformer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
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
    @Autowired val template: KafkaTemplate<Int, GenericRecord>
) {
    val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

    @KafkaListener(topics = ["\${topic}"], groupId = "G1", batch = "true")
    fun batch(records: ConsumerRecords<Int, UserCreateStatus>) {
        records.forEach { record ->
            process(record)
        }
    }

    private fun process(record: ConsumerRecord<Int, UserCreateStatus>) {
        logger.info("Transforming UserCreateStatus: ${record.value()}")
        val transformer = when (record.value().getStatus()) {
            "success" -> SuccessTransformer()
            "failed" -> FailTransformer()
            else -> throw RuntimeException("Unknown status")
        }
        KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG
        val transformed = transformer.transform(record.value())
        template.send("transformed", transformed)
    }

//    @KafkaListener(topics = ["\${topic}"], groupId = "G1")
//    fun batch(record: ConsumerRecord<Int, UserCreateStatus>) {
//        val map = HashMap<String, Any>()
//        map["message"] = record
//        map["createdAt"] = Date()
//        logger.error(mapper.writeValueAsString(map))
//    }
}