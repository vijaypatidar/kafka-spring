package com.example.kafka.mappers

import com.example.kafka.events.UserCreateStatus
import org.apache.avro.generic.GenericRecord

interface DataMapper<out T : GenericRecord> {
    fun transform(record: UserCreateStatus): T
}
