package com.examples.kafka.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
    @Id
    val id: String,
    val name: String,
    val age: Int?,
    val address: Address?
)