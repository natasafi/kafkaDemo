package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.stereotype.Component
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@EmbeddedKafka(ports = [9093], topics = ["natasa-topic-example"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(KafkaProducerTest.SomeListener::class)
class KafkaProducerTest {

    @Autowired
    private lateinit var producer: KafkaProducer

    @Autowired
    private lateinit var someListener: SomeListener

    @Test
    @DirtiesContext
    fun publishMessage() {
        // Given
        val user = User("id", "name", 20)

        // When
        producer.produceMessage(user)

        // Then
        val messageSent: User = someListener.waitForMessage()
        assertThat(messageSent).isEqualTo(user)
    }

    @Component
    class SomeListener {

        private val latch = CountDownLatch(1)
        private var message: User? = null

        @KafkaListener(topics = ["natasa-topic-example"], groupId = "natasa-message-consumer")
        fun consumer(user: User) {
            KotlinLogging.logger { }.info { "Message received [$user]" }
            message = user
            latch.countDown()
        }

        fun waitForMessage(): User {
            if(latch.await(5, TimeUnit.SECONDS)) return message!!
            else throw AssertionError("Message not received")
        }
    }
}