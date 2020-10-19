package com.examples.kafka.demo.security

import mu.KotlinLogging
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class PasswordEncoder() {

    fun hashPassword(password: String): String {

        val saltedPassword = "ek fevgein$password de tameloumenon"

        return BCrypt.hashpw(saltedPassword, BCrypt.gensalt(10))
    }

    fun checkPassword(password: String, hashedPassword: String) {
        if (BCrypt.checkpw(password, hashedPassword)) {
            logger.info("Password accepted.")
        } else {
            logger.info("Password denied.")
        }
    }
}

