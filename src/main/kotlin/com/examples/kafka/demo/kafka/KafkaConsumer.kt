package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }


@Component
class KafkaConsumer(val userRepository: UserRepository) {

    @KafkaListener(topics = ["natasa-topic-example"], groupId = "natasa-message-consumer")
    fun consumeMessage(deserialisedUser: User) {

        logger.info { "Your message is safe with me: $deserialisedUser" }

        userRepository.save(deserialisedUser)
    }
}