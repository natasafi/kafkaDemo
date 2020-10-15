package com.examples.kafka.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
    @Id
    val id: String,
    val name: String,
    val age: Int?,
    val address: Address? = null,
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    val email: String,
    val password: String
)