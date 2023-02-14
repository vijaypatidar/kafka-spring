package com.example.kafka.transformers

import org.apache.avro.generic.GenericRecord

interface Transformer<I : GenericRecord, T : GenericRecord> {
    fun transform(record: I): T
}