package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.CountDownLatch

private const val TOPIC_NAME: String = "users-topic"

@EmbeddedKafka(ports = [9093], topics = ["users-topic"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
class UserConsumerTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, User>

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun consumeMessage() {
        // Given
        val latch = CountDownLatch(2)

        val user1 = User("1", "Adam", 20)
        val user2 = User("2", "Peter", 30)

        given(userRepository.save(any<User>())).will {
            latch.countDown()
            user1
        }.will {
            latch.countDown()
            user2
        }

        // When
        kafkaTemplate.send(TOPIC_NAME, user1)
        kafkaTemplate.send(TOPIC_NAME, user2)

        // Then
        latch.await()
        Mockito.verify(userRepository).save(user1)
        Mockito.verify(userRepository).save(user2)
    }
}