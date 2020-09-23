package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
private const val TOPIC_NAME: String = "natasa-topic-example"

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, User>) {

    fun produceMessage(user: User) {
        logger.info { "Your message is being produced: $user" }
        kafkaTemplate.send(TOPIC_NAME, user)
    }
}