package com._112.asgard.heimdall.service

import com._112.asgard.heimdall.jpa.ERole
import com._112.asgard.heimdall.jpa.User
import com._112.asgard.heimdall.jwt.JwtProvider
import com._112.asgard.heimdall.model.LoginUser
import com._112.asgard.heimdall.model.NewUser
import com._112.asgard.heimdall.repository.RoleRepository
import com._112.asgard.heimdall.web.JwtRefreshResponse
import com._112.asgard.heimdall.web.JwtResponse
import com._112.asgard.heimdall.web.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class UserServiceImpl() : UserService {
    @Autowired
    lateinit var userRepository: com._112.asgard.heimdall.repository.UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var jwtProvider: JwtProvider
    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var encoder: PasswordEncoder
    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl
    override fun createUser(request: NewUser): ResponseEntity<*> {
        if(!userDetailsService.emailExists(request.email!!)) {

            // Creating user's account
            val user = User(null,
                    request.email!!,
                    encoder.encode(request.password),
                    true
            )
            user.roles = listOf(roleRepository.findByName(ERole.ROLE_ADMIN))
            userRepository.save(user)
            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(ResponseMessage("Email is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    override fun login(request: LoginUser): ResponseEntity<*> {
        val user = userDetailsService.loadUserByUsername(request.email!!)
        if(!user.isEnabled) throw DisabledException("This account has been disabled. Username: ${user.username}")
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password))
        SecurityContextHolder.getContext().authentication = authentication
        val accessToken: String = jwtProvider.generateJwtToken(user.username!!)
        val refreshToken: String = jwtProvider.generateJwtRefreshToken(user.username!!)
        val roles: List<String> = user.authorities.stream()
                .map { item -> item.authority }
                .collect(Collectors.toList())
        return ResponseEntity.ok(JwtResponse(accessToken,refreshToken, user.username, roles))
    }

    override fun refreshToken(token: String): ResponseEntity<*> {
        if(jwtProvider.validateJwtToken(token)){
            val name = jwtProvider.getUserNameFromJwtToken(token)
            if(!name.startsWith("Refresh-")) throw AuthenticationServiceException("Cant refresh with Access token")
            val user = userDetailsService.loadUserByUsername(name.substring(8))
            return ResponseEntity.ok(JwtRefreshResponse(jwtProvider.generateJwtToken(user.username), jwtProvider.generateJwtRefreshToken(user.username)))
        }
        throw AuthenticationServiceException("Failed to decode token")
    }
}