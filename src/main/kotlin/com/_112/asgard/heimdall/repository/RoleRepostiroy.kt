package com._112.asgard.heimdall.repository

import com._112.asgard.heimdall.jpa.ERole
import com._112.asgard.heimdall.jpa.Role
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param


interface RoleRepository : MongoRepository<Role, String> {

    fun findByName(@Param("name") name: ERole): Role
}