package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.repository.UserRepository
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class AddressConsumer(val userRepository: UserRepository) {

    @KafkaListener(topics = ["address-topic"], groupId = "natasa-message-consumer")
    fun consumeAddressOfExistingUser(record: ConsumerRecord<String, Address>) {

        logger.info { "Your address is safe with me: ${record.value()} with key ${record.key()}" }

        userRepository.updateAddressById(record.key(), record.value())
    }

}
