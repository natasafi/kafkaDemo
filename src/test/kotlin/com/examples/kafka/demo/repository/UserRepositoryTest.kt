package com.examples.kafka.demo.repository

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.models.createUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(SpringExtension::class)
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val mongoTemplate: MongoTemplate
) {

    @BeforeEach
    fun setUpRepository() {
        mongoTemplate.dropCollection("user")
    }

    @Nested
    inner class `Given the endpoint is accessed When GET users is requested` {
        @Test
        fun `Then it should return all users`() {

            // Given
            mongoTemplate.insert(createUserWithIdOf1())
            mongoTemplate.insert(createUserWithIdOf2())

            // When
            val actualUsers = userRepository.findAll()

            // Then
            assertThat(actualUsers).isEqualTo(
                listOf(
                    createUserWithIdOf1(),
                    createUserWithIdOf2()
                )
            )
        }
    }

    @Nested
    inner class `When accessing GET users endpoint  with name parameter` {

        @Test
        fun `Then should return all users by their requested names`() {
            // Given
            mongoTemplate.insert(createUserWithIdOf1())
            mongoTemplate.insert(createUserWithIdOf2())

            // When
            val actualUsers = userRepository.findUsersByName("Phillip Fry")

            // Then
            assertThat(actualUsers).isEqualTo(listOf(createUserWithIdOf2()))
        }
    }

    @Nested
    inner class `When accessing GET users endpoint searching by id` {

        @Test
        fun `Then should return all users by their requested id`() {
            // Given
            mongoTemplate.insert(createUserWithIdOf1())
            mongoTemplate.insert(createUserWithIdOf2())

            // When
            val actualUsers = userRepository.findUserById("1")

            // Then
            assertThat(actualUsers).isEqualTo(createUserWithIdOf1())
        }
    }

//    @Nested
//    inner class `When updating a user's address searching by id` {
//
//        @Test
//        fun `Then should save the user with the new address`() {
//            // Given
//            val user = createUserWithIdOf1()
//            mongoTemplate.insert(user)
//
//            // When
//            val address = createAddress()
//            userRepository.updateAddressById("1", address)
//            val actualUser = mongoTemplate.findById<User>("1")
//
//            // Then
//            assertThat(actualUser).isEqualTo(user.copy(address = address))
//        }
//    }
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

private fun createAddress() = Address(city = "Leeds")



