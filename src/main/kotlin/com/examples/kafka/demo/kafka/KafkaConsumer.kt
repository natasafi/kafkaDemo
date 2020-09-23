package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }


@Component
class KafkaConsumer(val userRepository: UserRepository, val objectMapper: ObjectMapper) {

    @KafkaListener(topics = ["natasa-topic-example"], groupId = "natasa-message-consumer")
    fun consumeMessage(message: String) {

        val deserialisedUser = objectMapper.readValue<User>(message)

        logger.info { "Your message is safe with me: $message and $deserialisedUser" }

        userRepository.save(deserialisedUser)
    }
}