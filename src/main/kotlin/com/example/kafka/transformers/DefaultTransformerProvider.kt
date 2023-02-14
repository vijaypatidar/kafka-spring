package com.example.kafka.transformers

import org.apache.avro.generic.GenericRecord
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultTransformerProvider : TransformerProvider, ApplicationContextAware {
    private lateinit var applicationContext: ApplicationContext
    override fun get(status: String): Transformer<GenericRecord> {
        return this.applicationContext.getBean(
            "${status}_TRANSFORMER".uppercase(Locale.getDefault()),
            Transformer::class.java
        )
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

}
