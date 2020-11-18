/*
package com._112.asgard.heimdall.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

// This is here to forward all unknown route requests to our vue app https://stackoverflow.com/questions/54987428/vue-js-spring-boot-redirect-to-homepage-on-404
@Controller
class RoutesController : ErrorController {
    @RequestMapping(value = [PATH])
    fun error(): String {
        return "forward:/"
    }

    override fun getErrorPath(): String {
        return PATH
    }

    companion object {
        private const val PATH = "/error"
    }
}*/
