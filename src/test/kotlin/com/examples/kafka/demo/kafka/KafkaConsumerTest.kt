package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension

private val logger = KotlinLogging.logger { }

@EmbeddedKafka(ports = [9093], topics = ["natasa-topic-example"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(KafkaConsumerTest.SomeProducer::class)
class KafkaConsumerTest {
    @Autowired
    private lateinit var consumer: KafkaConsumer

    @Autowired
    private lateinit var someProducer: SomeProducer

//    @Autowired
//    private lateinit var kafkaTemplate: KafkaTemplate

    @Test
    fun consumeMessage() {
        // Given
        val user = User("id", "name", 20)

        // When
        consumer.consumeMessage(user)

        // Then
        val messageConsumed: User = someProducer.waitForMessage()
        Assertions.assertThat(messageConsumed).isEqualTo(user)
    }

    @Component
    class SomeProducer(private val kafkaTemplate: KafkaTemplate<String, User>) {

        private val messages = mutableListOf<User>()

        fun producer(user: User) {
            logger.info { "Your message is produced:[$user]" }
            kafkaTemplate.send("natasa-topic-example", user)
        }

        fun waitForMessage(): User {
            var counter = 0
            do {
                if (messages.isNotEmpty()) return messages[0]
                else Thread.sleep(1000)
                counter++
            } while (counter < 10)
            throw AssertionError("No message was received")
        }
    }
}