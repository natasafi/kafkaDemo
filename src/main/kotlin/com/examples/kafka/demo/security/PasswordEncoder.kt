package com.examples.kafka.demo.security

import com.examples.kafka.demo.models.User
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class PasswordEncoder(val user: User) {
    val password: String = user.password

    val hashedPassword: String = BCrypt.hashpw(password, BCrypt.gensalt("ek fevgein de tameloumenon",10))

    fun checkPassword(password: String) {
        if (BCrypt.checkpw(password, hashedPassword)) {
            println("Password accepted.")
        } else {
            println("Password denied.")
        }
    }
}

