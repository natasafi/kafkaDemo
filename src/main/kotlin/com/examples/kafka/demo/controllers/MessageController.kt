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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/")
class MessageController(
    private val producer: KafkaProducer,
    private val userRepository: UserRepository
) {
    @GetMapping("/users")
    fun getUsers(@RequestParam(value = "name", required = false) name: String?): ResponseEntity<List<User>> {
        if (name != null) {
            if (name.isNotEmpty()) {
                val usersNames = userRepository.findUsersByName(name)

                logger.info { "Here's the users: $usersNames" }

                return ResponseEntity.ok(usersNames)
            }
        }
        val users = userRepository.findAll()

        logger.info { "Here's the users: $users" }

        return ResponseEntity.ok(users)

    }

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<User> {

        logger.info { "Here's the id: $id" }

        val userById = userRepository.findUserById(id)

        logger.info { "Here's the user: $userById" }

        return ResponseEntity.ok(userById)
    }

    @PostMapping(value = ["/user"])
    fun postUserJson(@RequestBody user: User): String? {

        producer.produceMessage(user)

        logger.info { "Here's your posted user: $user" }

        return "Your user was published successfully"
    }
}
