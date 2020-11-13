package com._112.asgard.heimdall

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
class ExampleController {

    @GetMapping("/api/hello", produces = ["application/json"])
    fun example() = "Hello World!"
}