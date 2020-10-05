package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.CountDownLatch

@EmbeddedKafka(ports = [9093], topics = ["natasa-topic-example"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
class KafkaConsumerTest {

    // Inject a real Kafka Template so we can publish messages to Kafka
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, User>

    // Inject a mock UserRepository that will be used by Spring
    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun consumeMessage() {
        // Given
        val latch = CountDownLatch(1) // Create a latch that expects to have its countDown method called once
        val user = User("id", "name", 20)
        given(userRepository.save(any<User>())).will { // Every time userRepository.save(...) is called this block of code will run
            latch.countDown() // Count down the latch
            user // Return the user
        }

        // When
        kafkaTemplate.send("natasa-topic-example", user) // Publish a message to Kafka

        // Then
        latch.await() // wait for the latch to be counted down by something calling userRepository.save()
        verify(userRepository).save(user) // Verify the repository was called with the right user
    }
}