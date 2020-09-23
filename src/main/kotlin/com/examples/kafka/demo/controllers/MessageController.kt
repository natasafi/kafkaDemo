package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/")
class MessageController(private val producer: KafkaProducer, private val userRepository: UserRepository) {
    @PostMapping("/message")
    fun publish(@RequestParam message: String) {
        producer.produceMessage(message)
    }

    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<User> {

        logger.info { "Here's the id: $userId" }

        val userById = userRepository.findByUserId(userId)

        logger.info { "Here's the user: $userById" }

        return ResponseEntity.ok(userById)
    }
}