package com._112.asgard.heimdall.repository

import com._112.asgard.heimdall.jpa.User

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*
import javax.transaction.Transactional

interface UserRepository: MongoRepository<User, String> {

    fun findByEmail(@Param("email") email: String): Optional<User>

    @Transactional
    fun deleteByEmail(@Param("email") email: String)

}