package com.examples.kafka.demo.models

data class UserResponse(
    val id: String,
    val name: String,
    val age: Int?,
    val address: Address? = null,
    val email: String
)
