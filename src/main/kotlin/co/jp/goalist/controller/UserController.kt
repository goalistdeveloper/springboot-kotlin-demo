package co.jp.goalist.controller

import co.jp.goalist.auth.UserAuth
import co.jp.goalist.models.CreatedResponse
import co.jp.goalist.models.Profile
import co.jp.goalist.models.User
import co.jp.goalist.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val passwordEncoder: BCryptPasswordEncoder, val service: UserService) {
    @PostMapping("/createUser")
    fun createUser(@RequestBody userAuth: UserAuth, @RequestBody profile: Profile): ResponseEntity<CreatedResponse> {
        val newUser = User(
            id = null,
            name = userAuth.username,
            password = passwordEncoder.encode(userAuth.password).toString(),
            profile = profile)
        service.save(newUser)
        return ResponseEntity<CreatedResponse>(HttpStatus.CREATED)
    }
}