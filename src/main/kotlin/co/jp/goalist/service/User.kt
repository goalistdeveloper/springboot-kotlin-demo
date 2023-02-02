package co.jp.goalist.service

import co.jp.goalist.models.User
import co.jp.goalist.models.UserSecurity
import co.jp.goalist.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Collections
import java.util.Optional

@Service
class UserService(val db: UserRepository): UserDetailsService {
    fun save(user: User) {
        db.save(user)
    }

    fun getById(id: String): Optional<User> {
        return db.findById(id)
    }

    fun getByNameAndPassword(name: String, password: String): User? {
        return db.findByNameAndPassword(name, password)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        // Create a method in your repo to find a user by its username
        val user = db.findUserByName(username) ?: throw UsernameNotFoundException("$username not found")
        return UserSecurity(
            user.id,
            user.name,
            user.password,
            Collections.singleton(SimpleGrantedAuthority("user"))
        )
    }
}