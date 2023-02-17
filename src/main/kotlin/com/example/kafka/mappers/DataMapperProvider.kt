package com.example.kafka.mappers

import org.apache.avro.generic.GenericRecord

interface DataMapperProvider {
    fun get(status: String): DataMapper<GenericRecord>
}
