package co.jp.goalist.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenUtil {

    private val secret = "GOALIST"
    private val expiration = 6000000

    fun generateToken(name: String, password: String): String =
        Jwts.builder().setClaims(mapOf("name" to name, "password" to password))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray()).compact()

    fun getClaims(token: String): Claims =
        Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate)
    }
}