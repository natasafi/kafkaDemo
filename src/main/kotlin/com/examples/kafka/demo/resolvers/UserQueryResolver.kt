package com.examples.kafka.demo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.examples.kafka.demo.models.User
import com.examples.kafka.demo.repository.UserRepository
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component


@Component
class UserQueryResolver(val userRepository: UserRepository, private val mongoOperations: MongoOperations) :
    GraphQLQueryResolver {

    fun users(): List<User> {
        return userRepository.findAll()
    }

}