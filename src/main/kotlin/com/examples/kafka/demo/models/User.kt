package com.examples.kafka.demo.models

import org.bson.types.ObjectId
import org.jetbrains.annotations.Nullable
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Column
import javax.persistence.Id

@Document(collection = "user")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),
    @Column
    val name: String,
    @Column
    @Nullable
    val age: Long?
)