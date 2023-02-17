package com.example.kafka.mappers

import com.example.kafka.events.UserCreateFailedEvent
import com.example.kafka.events.UserCreateStatus
import org.springframework.stereotype.Service

@Service("FAILED_DATA_MAPPER")
class FailedStatusDataMapper : DataMapper<UserCreateFailedEvent> {
    override fun transform(record: UserCreateStatus): UserCreateFailedEvent {
        return UserCreateFailedEvent.newBuilder()
            .setName(record.getName())
            .setRequestId(record.getRequestId())
            .build()
    }
}