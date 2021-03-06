package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.models.UserLogin
import com.examples.kafka.demo.models.UserResponse
import com.examples.kafka.demo.repository.UserRepository
import com.examples.kafka.demo.security.PasswordEncoder
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/")
class MessageController(
    private val producer: KafkaProducer,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @GetMapping("/users")
    fun getUsers(@RequestParam(value = "name", required = false) name: String?): ResponseEntity<List<UserResponse>> {
        val users: List<User> =
            name?.takeIf { it.isNotEmpty() }?.let { userRepository.findUsersByName(it) } ?: userRepository.findAll()

        val usersView = users.map { user -> user.toUserView() }

        logger.info { "Here's the users: $usersView" }

        return ResponseEntity.ok(usersView)
    }

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserResponse> {

        logger.info { "Here's the id: $id" }

        val userById = userRepository.findUserById(id).toUserView()

        logger.info { "Here's the user: $userById" }

        return ResponseEntity.ok(userById)
    }

    @PostMapping(value = ["/user"])
    fun postUser(@RequestBody user: User): String? {

        producer.produceUser(user)

        logger.info { "Here's your posted user: $user" }

        return "Your user was published successfully"
    }

    @PostMapping(value = ["/login"])
    fun postUserLogin(@RequestBody user: UserLogin) {

        val userByEmail = userRepository.findUserByEmail(user.email)

        logger.info { "Here's your posted user: $user" }

        return passwordEncoder.checkPassword(user.password, userByEmail.password)

    }

    @PutMapping(value = ["/user/{id}/address"])
    fun putAddress(@PathVariable id: String, @RequestBody address: Address): String? {

        producer.produceAddress(id, address)

        return address.toString()
    }

    fun User.toUserView() = UserResponse(
        id = id,
        name = name,
        address = address,
        age = age,
        email = email
    )

    fun User.toUserLogin() = UserLogin(
        email = email,
        password = password
    )
}
