package com.examples.kafka.demo.repository

import com.examples.kafka.demo.models.Address
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : MongoRepository<Address, String>