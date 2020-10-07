package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

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

            val typeReference = object : ParameterizedTypeReference<List<User>>() {}
            val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("/users"))

            // When
            val response = restTemplate.exchange(requestEntity, typeReference)

            println("Here's that: ${response.body} ")
            println("users :$users")

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).hasSize(2)
            assertThat(response.body).containsAll(users)
        }

        @Test
        fun shouldReturn200WhenUsersByName() {
            val users =
                listOf(User("2", "Phillip Fry", 25))
            // Given
            given(userRepository.findUsersByName("Phillip Fry")).willReturn(users)

            val typeReference = object : ParameterizedTypeReference<List<User>>() {}
            val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("/users?name=Phillip%20Fry"))

            // When
            val response = restTemplate.exchange(requestEntity, typeReference)

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).isEqualTo(users)
        }

        @Test
        fun shouldReturn200WhenUsersById() {
            val user = User("100", "Pickle Rick", 64)
            // Given
            given(userRepository.findUserById("1")).willReturn(user)

            // When
            val response = restTemplate.getForEntity<User>("/user/1")

            // Then
            assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(response.body).isEqualTo(user)
        }
    }
}
