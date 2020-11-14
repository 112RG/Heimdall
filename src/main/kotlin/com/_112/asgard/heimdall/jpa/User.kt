package com._112.asgard.heimdall.jpa

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document(collection = "users")
data class User (

        @Id
        val id: Long,

        @NotBlank
        @Size(max = 50)
        @Email
        var email: String?=null,

        @NotBlank
        @Size(max = 20)
        var username: String?=null,

        @NotBlank
        @Size(max = 120)
        var password: String?=null,

        @NotBlank
        var enabled: Boolean = true,

        var roles: Collection<Role>? = null
)