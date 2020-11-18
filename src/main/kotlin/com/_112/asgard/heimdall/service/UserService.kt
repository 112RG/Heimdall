package com._112.asgard.heimdall.service

import com._112.asgard.heimdall.model.LoginUser
import com._112.asgard.heimdall.model.NewUser
import org.springframework.http.ResponseEntity

interface UserService {

    fun createUser(request: NewUser): ResponseEntity<*>

    fun login(request: LoginUser): ResponseEntity<*>
    fun refreshToken(token: String): ResponseEntity<*>
}