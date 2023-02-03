package co.jp.goalist.repository

import co.jp.goalist.models.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository: CrudRepository<User, String> {
    @Query("select * from users u where u.name=:name and u.password=:password limit 1")
    fun findByNameAndPassword(@Param("name") name: String, @Param("password") password: String): User?

    @Query("select * from users u where u.name=:name")
    fun findUserByName(@Param("name") name: String): User?
}
