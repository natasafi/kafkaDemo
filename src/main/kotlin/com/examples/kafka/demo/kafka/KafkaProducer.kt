package com.examples.kafka.demo.kafka

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

private var kafkaTemplate: KafkaTemplate<String, String>? = null

@Component
class KafkaProducer() {

    fun produceMessage(message: String) {
        logger.info { "Your message is being produced: $message" }
        kafkaTemplate?.send("natasa-topic-example", message)
    }
}