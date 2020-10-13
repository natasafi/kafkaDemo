package com.examples.kafka.demo.models

fun createUser(
    id: String = "1",
    name: String = "Pickle Rick",
    age: Int? = 64
) = User(
    id = id,
    name = name,
    age = age
)
