package com._112.asgard.heimdall.controller


import com._112.asgard.heimdall.jpa.ERole
import com._112.asgard.heimdall.jpa.User
import com._112.asgard.heimdall.jwt.JwtProvider
import com._112.asgard.heimdall.model.LoginUser
import com._112.asgard.heimdall.model.NewUser
import com._112.asgard.heimdall.repository.RoleRepository
import com._112.asgard.heimdall.repository.UserRepository
import com._112.asgard.heimdall.web.JwtResponse
import com._112.asgard.heimdall.web.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginUser): ResponseEntity<*> {
        val userCandidate: Optional<User> = userRepository.findByEmail(loginRequest.email!!)

         if (userCandidate.isPresent) {
            val user: User = userCandidate.get()
            val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            SecurityContextHolder.getContext().setAuthentication(authentication)
            val jwt: String = jwtProvider.generateJwtToken(user.email!!)
            val authorities: List<GrantedAuthority> = user.roles!!.stream().map({ role -> SimpleGrantedAuthority(role.name) }).collect(Collectors.toList<GrantedAuthority>())
            return ResponseEntity.ok(JwtResponse(jwt, user.email, authorities))
        } else {
            return ResponseEntity(ResponseMessage("User not found!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUser): ResponseEntity<*> {

        val userCandidate: Optional<User> = userRepository.findByEmail(newUser.email!!)

        if (!userCandidate.isPresent) {
        if (emailExists(newUser.email!!)) {
                return ResponseEntity(ResponseMessage("Email is already in use!"),
                        HttpStatus.BAD_REQUEST)
            }

            // Creating user's account
            val user = User(
                    0,
                    newUser.email!!,
                    "None",
                    encoder.encode(newUser.password),
                    true
            )
            user!!.roles = Arrays.asList(roleRepository.findByName(ERole.ROLE_ADMIN))
            userRepository.save(user)

            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(ResponseMessage("User already exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

}