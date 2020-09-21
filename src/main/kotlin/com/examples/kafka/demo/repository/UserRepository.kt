package com.examples.kafka.demo.repository

import com.examples.kafka.demo.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findById(id: ObjectId): User
}