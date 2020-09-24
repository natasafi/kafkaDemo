package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/")
class MessageController(
    private val producer: KafkaProducer,
    private val userRepository: UserRepository
) {
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> {

        val users = userRepository.findAll()

        logger.info { "Here's the users: $users" }

        return ResponseEntity.ok(users)
    }

    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<User> {

        logger.info { "Here's the id: $userId" }

        val userById = userRepository.findByUserId(userId)

        logger.info { "Here's the user: $userById" }

        return ResponseEntity.ok(userById)
    }

    @PostMapping(value = ["/user/postedUser"])
    fun postUserJson(@RequestBody postUser: User): String? {

        producer.produceMessage(postUser)

        logger.info { "Here's your posted user: $postUser" }

        return "Your user was published successfully"
    }
}
