package com.example.kafka.mappers

import org.apache.avro.generic.GenericRecord
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultDataMapperProvider : DataMapperProvider, ApplicationContextAware {
    private lateinit var applicationContext: ApplicationContext
    override fun get(status: String): DataMapper<GenericRecord> {
        return this.applicationContext.getBean(
            "${status}_DATA_MAPPER".uppercase(Locale.getDefault()),
            DataMapper::class.java
        )
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

}
