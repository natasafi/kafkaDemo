package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }


@Component
class UserConsumer(val userRepository: UserRepository) {

    @KafkaListener(topics = ["users-topic"], groupId = "natasa-message-consumer")
    fun consumeMessage(record: ConsumerRecord<String, User>) {

        logger.info { "Your user is safe with me: ${record.value()} with key ${record.key()}" }

        userRepository.save(record.value())
    }
}