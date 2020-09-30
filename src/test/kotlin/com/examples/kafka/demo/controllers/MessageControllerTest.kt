package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerTest {

    @MockBean
    private lateinit var producer: KafkaProducer

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Nested
    inner class GETUsers {

        @Test
        fun shouldReturn200WhenNoUsers() {
            // Given
            given(userRepository.findAll()).willReturn(emptyList())

            // When
            val response = restTemplate.getForEntity<List<User>>("/users")

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        }

        @Test
        fun shouldReturn200WhenUsers() {
            val users =
                listOf(User("1", "Pickle Rick", 64), User("2", "Phillip Fry", 25))
            // Given
            given(userRepository.findAll()).willReturn(users)

            // When
            val response = restTemplate.getForEntity<List<User>>("/users")

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).hasSize(2)
//            assertThat(response.body).containsExactlyInAnyOrderElementsOf(users)
        }

        @Test
        fun shouldReturn200WhenUsersById() {
            val users =
                listOf(User("1", "Pickle Rick", 64), User("2", "Phillip Fry", 25))
            // Given
            given(userRepository.findUserById("1")).willReturn(users.component1())

            // When
            val response = restTemplate.getForEntity<User>("/user/1")

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).isEqualTo(users.component1())
        }
    }
}
