package com._112.asgard.heimdall.controller

import com._112.asgard.heimdall.jpa.Image
import com._112.asgard.heimdall.jpa.User
import com._112.asgard.heimdall.repository.ImageRepository
import com._112.asgard.heimdall.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import kotlin.random.Random


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class TestController {
    @Autowired
    lateinit var imageRepository: ImageRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @GetMapping("/add")
    fun addImages(): String {
        val image = Image(null, Random(345634634564).toString(), "Test", "a","a","a","a")
        imageRepository.save(image)
        return "Public Content."
    }
    @GetMapping("/images")
    @ResponseBody
    fun getImages(@RequestParam("page") page: Int): Page<Image> {
        val images: Page<Image> = imageRepository.findByUserId("Test", PageRequest.of(page, 10))
        return images
    }
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    fun userAccess(authentication: Authentication): String {
        val user: User = userRepository.findByEmail(authentication.name).get()
        return "Hello " + user.email + " " + user.roles + "!"
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): String {
        return "Moderator Board."
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}