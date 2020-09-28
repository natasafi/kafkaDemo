package com.examples.kafka.demo

import com.examples.kafka.demo.models.createUser
import com.examples.kafka.demo.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest
@ExtendWith(SpringExtension::class)
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val mongoTemplate: MongoTemplate
) {

    @BeforeEach
    fun setUpRepository() {
        userRepository.deleteAll()
    }

    @Nested
    inner class `When accessing GET users endpoint` {
        @Test
        fun `Then should return all users`() {
            // Given
            mongoTemplate.insert(createUserWithIdOf1())
            mongoTemplate.insert(createUserWithIdOf2())

            // When
            val actualUsers = userRepository.findAll()

            // Then
            assertThat(actualUsers).isEqualTo(listOf(createUserWithIdOf1(), createUserWithIdOf2()))
        }
    }

    @Nested
    inner class `When accessing GET users endpoint  with name parameter` {

        @Test
        fun `Then should return all users by their requested names`() {

        }
    }

    @Nested
    inner class `When accessing POST user endpoint` {
        @Test
        fun `should post a user`() {
        }
    }
}

private fun createUserWithIdOf1() =
    createUser(
        id = "1",
        name = "Pickle Rick",
        age = 64
    )

private fun createUserWithIdOf2() =
    createUser(
        id = "2",
        name = "Phillip Fry",
        age = 25
    )


