package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.security.PasswordEncoder
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
private const val USERS_NAME: String = "users-topic"
private const val ADDRESS_NAME: String = "address-topic"

@Service
class KafkaProducer(
    private val userTemplate: KafkaTemplate<String, User>,
    private val addressTemplate: KafkaTemplate<String, Address>,
    private val encoder: PasswordEncoder
) {

    fun produceUser(user: User) {
        val hashedPassword = encoder.hashPassword(user.password)

        logger.info { "Your message is being produced: $user" }

        userTemplate.send(USERS_NAME, user.id, user.copy(password = hashedPassword))
    }

    fun produceAddress(userId: String, address: Address) {
        logger.info { "Your address is being produced: $address with $userId" }
        addressTemplate.send(ADDRESS_NAME, userId, address)
    }
}