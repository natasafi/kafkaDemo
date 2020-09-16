package com.examples.kafka.demo.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

private var kafkaTemplate: KafkaTemplate<String, String>? = null

@Component
class KafkaProducer() {

    fun produceMessage(message: String) {
        kafkaTemplate?.send("natasa-topic-example", message)
    }
}