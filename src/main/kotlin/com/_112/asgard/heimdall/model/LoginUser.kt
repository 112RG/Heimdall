package com._112.asgard.heimdall.model


import com.fasterxml.jackson.annotation.JsonProperty
import com.sun.istack.NotNull
import java.io.Serializable
import javax.validation.constraints.NotBlank

class LoginUser : Serializable {

    @JsonProperty("email")
    @NotBlank(message = "Email must be provided")
    var email: String? = null

    @JsonProperty("password")
    @NotBlank(message = "Password must be provided")
    var password: String? = null

    @JsonProperty("recapctha_token")
    var recaptchaToken: String? = null

    constructor() {}

    constructor(email: String, password: String, recaptchaToken: String) {
        this.email = email
        this.password = password
        this.recaptchaToken = recaptchaToken
    }

    companion object {

        private const val serialVersionUID = -1764970284520387975L
    }
}