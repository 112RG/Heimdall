package com._112.asgard.heimdall.service

import com._112.asgard.heimdall.repository.RoleRepository
import com._112.asgard.heimdall.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val userCandidate = userRepository.findByEmail(email)
        if(userCandidate.isPresent){
            val user = userCandidate.get()
            val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.name) }.collect(Collectors.toList<GrantedAuthority>())
            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(user.password)
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build()
        } else {
            throw UsernameNotFoundException("User not found")
        }

    }

     fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }
}