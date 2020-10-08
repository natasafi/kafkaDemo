package com.examples.kafka.demo.models

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "address")
data class Address(
    val city: String
)