package com.examples.kafka.demo.kafka

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import org.apache.kafka.clients.consumer.ConsumerRecord
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

@EmbeddedKafka(ports = [9093], topics = ["users-topic", "address-topic"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(value = [KafkaProducerTest.UserListener::class, KafkaProducerTest.AddressListener::class])
class KafkaProducerTest {
    @Autowired
    private lateinit var producer: KafkaProducer

    @Autowired
    private lateinit var usersListener: UserListener

    @Autowired
    private lateinit var addressListener: AddressListener

    @Test
    @DirtiesContext
    fun publishUserMessage() {
        // Given
        val expectedUser = User("id", "name", 20)

        // When
        producer.produceUser(expectedUser)

        // Then
        val (key, actualUser) = usersListener.waitForMessage()
        assertThat(actualUser).isEqualTo(expectedUser)
        assertThat(key).isEqualTo(expectedUser.id)
    }

    @Test
    @DirtiesContext
    fun publishAddressMessage() {
        // Given
        val address = Address("Leeds")

        // When
        producer.produceAddress("1", address)

        // Then
        val (key, actualAddress) = addressListener.waitForMessage()
        assertThat(key).isEqualTo("1")
        assertThat(actualAddress).isEqualTo(address)
    }

    open class KafkaTestListener<K, V> {
        protected val messages = mutableListOf<Pair<K, V>>()
        fun waitForMessage(): Pair<K, V> {
            var counter = 0
            do {
                if (messages.isNotEmpty()) return messages[0]
                else Thread.sleep(1000)
                counter++
            } while (counter < 10)
            throw AssertionError("No message was received")
        }

    }

    @Component
    class UserListener : KafkaTestListener<String, User>() {

        @KafkaListener(topics = ["users-topic"], groupId = "natasa-message-consumer")
        fun consumer(userRecord: ConsumerRecord<String, User>) =
            messages.add(userRecord.key() to userRecord.value())
    }

    @Component
    class AddressListener : KafkaTestListener<String, Address>() {

        @KafkaListener(topics = ["address-topic"], groupId = "natasa-message-consumer")
        fun consumer(addressRecord: ConsumerRecord<String, Address>) =
            messages.add(addressRecord.key() to addressRecord.value())
    }
}