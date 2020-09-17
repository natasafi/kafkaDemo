package com.examples.kafka.demo.controllers

import com.examples.kafka.demo.kafka.KafkaProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class MessageController(private val producer: KafkaProducer) {
    @PostMapping("/message")
    fun publish(@RequestParam message: String) {
        producer.produceMessage(message)
    }
}