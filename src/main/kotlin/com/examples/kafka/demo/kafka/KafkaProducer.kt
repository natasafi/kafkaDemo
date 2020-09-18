package com.examples.kafka.demo.kafka

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
private const val topic: String = "natasa-topic-example"

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun produceMessage(message: String) {
        logger.info { "Your message is being produced: $message" }
        kafkaTemplate.send(topic, message)
    }
}