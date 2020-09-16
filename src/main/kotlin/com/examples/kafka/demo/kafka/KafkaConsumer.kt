package com.examples.kafka.demo.kafka

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["natasa-topic-example"], groupId = "natasa-message-consumer")
    fun consumeMessage(message: String) {

        logger.info { "Your message is safe with me: $message" }
    }
}