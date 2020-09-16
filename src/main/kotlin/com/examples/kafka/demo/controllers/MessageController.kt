package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class MessageController(private val producer: KafkaProducer) {
    @PostMapping("/message")
    fun publish(@RequestBody message: String) {
        producer.produceMessage(message)
    }
}