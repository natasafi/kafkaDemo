package com.examples.kafka.demo.kafka

import mu.KotlinLogging
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension


@EmbeddedKafka
@ExtendWith(SpringExtension::class)
@SpringBootTest()
class KafkaProducerTest {
    private val logger = KotlinLogging.logger { }

    private val TOPIC_NAME = "natasa-topic-example"

    @MockBean
    private lateinit var container: KafkaMessageListenerContainer<String, String>

    @AfterAll
    fun tearDown() {
        container.stop()
    }
}