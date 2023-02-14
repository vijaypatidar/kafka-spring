package com.example.kafka.transformers

import com.example.kafka.events.UserCreateFailedEvent
import com.example.kafka.events.UserCreateStatus
import org.springframework.stereotype.Service

@Service("FAILED_TRANSFORMER")
class FailTransformer : Transformer<UserCreateFailedEvent> {
    override fun transform(record: UserCreateStatus): UserCreateFailedEvent {
        return UserCreateFailedEvent.newBuilder()
            .setName(record.getName())
            .setRequestId(record.getRequestId())
            .build()
    }
}