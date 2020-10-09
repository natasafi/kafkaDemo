package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
private const val USERS_NAME: String = "users-topic"

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, User>) {

    fun produceUser(user: User) {
        logger.info { "Your message is being produced: $user" }
        kafkaTemplate.send(USERS_NAME, user.id, user)
    }

    fun produceAddress(userId: String, expectedAddress: Address) {
        TODO("Not yet implemented")
    }

}