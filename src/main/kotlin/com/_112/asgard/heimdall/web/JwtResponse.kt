package com._112.asgard.heimdall.web

class JwtResponse(var accessToken: String?, var refreshToken: String?, var username: String?, val roles: List<String>) {
    var type = "Bearer"
}