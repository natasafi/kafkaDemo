package com.examples.kafka.demo.models

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document(collection = "user")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val age: Long?,
    @Transient
    var address: List<Address> = ArrayList()
)