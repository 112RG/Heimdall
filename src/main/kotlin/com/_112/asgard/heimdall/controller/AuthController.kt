package com._112.asgard.heimdall.controller

import com._112.asgard.heimdall.jwt.JwtProvider
import com._112.asgard.heimdall.model.LoginUser
import com._112.asgard.heimdall.model.NewUser
import com._112.asgard.heimdall.repository.RoleRepository
import com._112.asgard.heimdall.service.UserDetailsServiceImpl
import com._112.asgard.heimdall.service.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var userService: UserServiceImpl
    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var encoder: PasswordEncoder
    @Autowired
    lateinit var jwtProvider: JwtProvider

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginUser): ResponseEntity<*> {
        return userService.login(loginRequest)
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUser): ResponseEntity<*> {
        return userService.createUser(newUser)
    }
}