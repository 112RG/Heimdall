package com._112.asgard.heimdall.jwt


import com._112.asgard.heimdall.repository.UserRepository
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Date


@Component
public class JwtProvider {

    private val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    @Autowired
    lateinit var userRepository: UserRepository

    var jwtSecret: String = "7b6ed5302e28f8ea9a140d297aae438d5658c97f6f84d8e98886b09411d5f9c9f11920a8851fddde43e604bcf6da9fe10a0f6cc4f2ec2750d77e514340871254"

    var jwtExpiration:Int?=35

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
                .setSubject("Access-"+username)
                .setIssuedAt(Date())
                .setExpiration(Date((Date()).getTime() + jwtExpiration!! * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }
    fun generateJwtRefreshToken(username: String): String {
        return Jwts.builder()
                .setSubject("Refresh-"+username)
                .setIssuedAt(Date())
                .setExpiration(Date((Date()).getTime() + 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token -> Message: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty -> Message: {}", e)
        }

        return false
    }

    fun getUserNameFromJwtToken(token: String): String {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).body.subject
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token -> Message: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty -> Message: {}", e)
        }
        return ""
    }
}