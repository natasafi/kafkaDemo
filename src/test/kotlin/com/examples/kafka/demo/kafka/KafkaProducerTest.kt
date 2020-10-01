package com.examples.kafka.demo.kafka

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.rule.EmbeddedKafkaRule
import org.springframework.test.context.junit.jupiter.SpringExtension


@EmbeddedKafka
@ExtendWith(SpringExtension::class)
@SpringBootTest()
class KafkaProducerTest {

    private val TOPIC_NAME = "natasa-topic-example"

    @ExtendWith
    var embeddedKafkaRule = EmbeddedKafkaRule(1, true, TOPIC_NAME)

    @Autowired
    private lateinit var embeddedKafkaBroker: EmbeddedKafkaBroker

    @MockBean
    private lateinit var container: KafkaMessageListenerContainer<String, String>

    @BeforeAll
    fun setUp() {


    @AfterAll
    fun tearDown() {
        container.stop()
    }

}