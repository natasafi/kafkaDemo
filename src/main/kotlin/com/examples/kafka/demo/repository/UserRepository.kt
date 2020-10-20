package com.examples.kafka.demo.repository

import com.examples.kafka.demo.models.Address
import com.examples.kafka.demo.models.User
import com.mongodb.client.result.UpdateResult
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>, AddressFunctions {
    fun findUserById(id: String): User

    fun findUsersByName(name: String?): List<User>

    fun findUserByEmail(email: String): User
}

interface AddressFunctions {
    fun updateAddressById(id: String, address: Address): Long
}

class AddressFunctionsImpl(private val mongoTemplate: MongoTemplate) : AddressFunctions {
    override fun updateAddressById(id: String, address: Address): Long {
        val query = Query(Criteria.where("id").`is`(id))
        val update = Update()
        update.set("address", address)

        val result: UpdateResult = mongoTemplate.updateFirst(query, update, User::class.java)

        return result.modifiedCount
    }
}