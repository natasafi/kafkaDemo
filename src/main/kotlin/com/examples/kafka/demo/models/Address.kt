package com.examples.kafka.demo.models

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "address")
data class Address(
    val userId: String,
    val postcode: String,
    val city: String
)