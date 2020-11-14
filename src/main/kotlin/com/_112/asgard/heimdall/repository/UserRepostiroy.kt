package com._112.asgard.heimdall.repository

import java.util.Optional
import com._112.asgard.heimdall.jpa.User
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import javax.transaction.Transactional

interface UserRepository: MongoRepository<User, String> {

    fun existsByUsername(@Param("username") username: String): Boolean

    fun findByEmail(@Param("email") email: String): Optional<User>

    @Transactional
    fun deleteByUsername(@Param("username") username: String)

}