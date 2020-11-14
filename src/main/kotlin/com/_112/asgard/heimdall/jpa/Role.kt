package com._112.asgard.heimdall.jpa

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*

@Document(collection = "roles")
data class Role (

        @Id
        val id: String,
        val name: String

)