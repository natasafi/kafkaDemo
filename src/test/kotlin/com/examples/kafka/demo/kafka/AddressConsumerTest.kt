package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willDoNothing
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition

private const val ADDRESS_TOPIC: String = "address-topic"

@EmbeddedKafka(ports = [9093])
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [
        AddressConsumer::class,
        KafkaAutoConfiguration::class
    ]
)
class AddressConsumerTest {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Address>

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun consumeAddressOfExistingUser() {
        val user = User("1", "Adam", 20)
        val address = Address("Huddersfield")
        val latch = CountDownLatch(1)
        given(userRepository.updateAddressById(user.id, address)).will {
            latch.countDown()
            Unit
        }

        kafkaTemplate.send(ADDRESS_TOPIC, user.id, address)
            .addCallback({
                latch.await(5, TimeUnit.SECONDS)
                verify(userRepository).updateAddressById(user.id, address)
            },
                { fail("Couldn't send message $it") })
    }
}