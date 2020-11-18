package com._112.asgard.heimdall.repository

import com._112.asgard.heimdall.jpa.ERole
import com._112.asgard.heimdall.jpa.Image
import com._112.asgard.heimdall.jpa.Role
import com._112.asgard.heimdall.jpa.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.*


interface ImageRepository : MongoRepository<Image, String> {
    //fun findByIdPage(imageId: String, pageable: Pageable): Page<Image>

    fun findByImageId(id: String): Optional<Image>
    fun findByUserId(userId: String, pageable: Pageable): Page<Image>

}