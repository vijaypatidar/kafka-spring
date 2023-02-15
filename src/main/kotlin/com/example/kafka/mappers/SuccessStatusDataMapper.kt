package com.example.kafka.mappers

import com.example.kafka.events.UserCreateStatus
import com.example.kafka.events.UserCreatedEvent
import org.springframework.stereotype.Service

@Service("SUCCESS_DATA_MAPPER")
class SuccessStatusDataMapper : DataMapper<UserCreatedEvent> {
    override fun transform(record: UserCreateStatus): UserCreatedEvent {
        return UserCreatedEvent.newBuilder()
            .setName(record.getName())
            .setUsername(record.getName())
            .setRequestId(record.getRequestId())
            .build()
    }
}