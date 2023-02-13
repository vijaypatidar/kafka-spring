package com.example.kafka.kafka

import com.example.kafka.events.User
import com.example.kafka.kafka.deserializer.UserDeserializer
import com.example.kafka.kafka.serializer.UserSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*


@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.properties.bootstrap.servers}") val bootstrapAddress: String,
    @Value("\${spring.kafka.properties.sasl.jaas.config}") val jaasConfig: String,
    @Value("\${spring.kafka.group}") val group: String,
    @Value("\${spring.kafka.properties.sasl.mechanism}") val mechanism: String,
    @Value("\${spring.kafka.properties.security.protocol}") val protocol: String
) {

    fun properties(): MutableMap<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props["bootstrap.servers"] = bootstrapAddress
        props["group.id"] = group
        props["sasl.jaas.config"] = jaasConfig
        props["sasl.mechanism"] = mechanism
        props["security.protocol"] = protocol
        props["session.timeout.ms"] = "30000";
        return props;
    }

    @Bean
    fun userConsumerFactory(): ConsumerFactory<String, User> {
        val config = properties()
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = UserDeserializer::class.java
        config[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 100
        return DefaultKafkaConsumerFactory<String, User>(config)
    }

    @Bean
    fun userProducerFactory(): ProducerFactory<String, User> {
        val config = properties()
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = UserSerializer::class.java
        return DefaultKafkaProducerFactory<String, User>(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, User>): ConcurrentKafkaListenerContainerFactory<String, User> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, User>()
        factory.consumerFactory = consumerFactory
        return factory
    }

    @Bean
    fun userKafkaTemplate(producerFactory: ProducerFactory<String, User>): KafkaTemplate<String, User> {
        return KafkaTemplate(producerFactory);
    }
}