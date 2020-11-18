package com._112.asgard.heimdall.jpa

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Document(collection = "images")
data class Image (

        @Id
        val id: String?,
        @NotBlank
        val imageId: String?=null,
        @NotBlank
        val userId: String?=null,
        @NotBlank
        val uploadedDate: String?,
        @NotBlank
        val mimeType: String?,
        @NotBlank
        val size: String?,
        @NotBlank
        val hash: String?
)