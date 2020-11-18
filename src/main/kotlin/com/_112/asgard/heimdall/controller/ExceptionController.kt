package com._112.asgard.heimdall.controller

import com._112.asgard.heimdall.web.ResponseMessage
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException

@Controller
@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFound(e: UsernameNotFoundException): ResponseEntity<*> {
        return ResponseEntity(ResponseMessage(e.message), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(BadCredentialsException::class)
    fun handleUserNotFound(e: BadCredentialsException): ResponseEntity<*> {
        return ResponseEntity(ResponseMessage(e.message), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<*>{
        val errors = mutableListOf<String>()
        e.bindingResult.fieldErrors.forEach { errors.add("${it.field}: ${it.defaultMessage}") }
        e.bindingResult.globalErrors.forEach { errors.add("${it.objectName}: ${it.defaultMessage}") }
        return ResponseEntity((errors), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(DisabledException::class)
    fun handleDisabled(e: DisabledException): ResponseEntity<*>{
        return ResponseEntity(ResponseMessage(e.message), HttpStatus.UNAUTHORIZED)
    }
    //Direct all non error traffic to vue frontend
    //@RequestMapping(value = ["/**/{path:[^.]*}"])
    @RequestMapping(value = ["/user/**", "/admin/**"])
    fun handleVue(): String? {
        return "forward:/"
    }
}