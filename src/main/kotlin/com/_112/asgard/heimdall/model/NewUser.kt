package com._112.asgard.heimdall.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class NewUser(email: String, password: String) : Serializable {

    @JsonProperty("email")
    var email: String? = email

    @JsonProperty("password")
    var password: String? = password

    companion object {
        private const val serialVersionUID = -1764970284520387975L
    }
}