package com.examples.kafka.demo.kafka.address

import com.examples.kafka.demo.models.Address
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
private const val TOPIC_NAME: String = "natasa-topic-example"

@Service
class AddressProducer(private val kafkaTemplate: KafkaTemplate<String, Address>) {

    fun produceAddressMessage(address: Address) {
        logger.info { "Your message is being produced: $address" }
        kafkaTemplate.send(TOPIC_NAME, address)
    }
}