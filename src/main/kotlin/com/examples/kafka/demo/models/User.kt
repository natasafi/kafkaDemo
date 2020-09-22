package com.examples.kafka.demo.models

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document(collection = "user")
data class User(
    @Id
    val userId: String,
    val name: String,
    val age: Int?
)