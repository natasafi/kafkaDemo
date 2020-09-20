package com.examples.kafka.demo.models

import org.jetbrains.annotations.Nullable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User (
    @Id
    val id: Long,
    @Column
    val name: String,
    @Column
    @Nullable
    val age: Long?
)