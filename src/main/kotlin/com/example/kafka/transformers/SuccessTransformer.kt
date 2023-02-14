package com.example.kafka.transformers

import com.example.kafka.events.UserCreateStatus
import com.example.kafka.events.UserCreatedEvent
import org.springframework.stereotype.Service

@Service("SUCCESS_TRANSFORMER")
class SuccessTransformer : Transformer<UserCreatedEvent> {
    override fun transform(record: UserCreateStatus): UserCreatedEvent {
        return UserCreatedEvent.newBuilder()
            .setName(record.getName())
            .setUsername(record.getName())
            .setRequestId(record.getRequestId())
            .build()
    }
}