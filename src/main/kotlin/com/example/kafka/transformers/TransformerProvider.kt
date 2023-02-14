package com.example.kafka.transformers

import org.apache.avro.generic.GenericRecord

interface TransformerProvider {
    fun get(status: String): Transformer<GenericRecord>
}
