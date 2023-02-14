package com.example.kafka.transformers

import com.example.kafka.events.UserCreateStatus
import org.apache.avro.generic.GenericRecord

interface Transformer<out T : GenericRecord> {
    fun transform(record: UserCreateStatus): T
}