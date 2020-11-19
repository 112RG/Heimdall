package com._112.asgard.heimdall.controller

import com._112.asgard.heimdall.jwt.JwtProvider
import com._112.asgard.heimdall.repository.RoleRepository
import com._112.asgard.heimdall.service.UserDetailsServiceImpl
import com._112.asgard.heimdall.service.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.security.SecureRandom
import java.util.*
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/image")
class ImageController {

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

    @PostMapping("/upload")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun registerUser(@Valid @RequestBody image: MultipartFile): ResponseEntity<*> {
        try {
            val randomString = UUID.randomUUID().toString().substring(0, 6)
            var ext = MimeTypeUtils.parseMimeType(image.contentType!!).subtype
            var fileName = Base64.getUrlEncoder().encodeToString(randomString.toByteArray()) + "." + ext
            image.transferTo(File(System.getProperty("user.dir") + "\\" + fileName))
            return ResponseEntity.ok("Ok")
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.ok("Bad")
        }
    }

}