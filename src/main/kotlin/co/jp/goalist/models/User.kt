package co.jp.goalist.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import java.util.regex.Pattern
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table("users")
data class User(@Id val id: String?, val name: String, val password: String,
                @MappedCollection(idColumn = "id") val profile: Profile)

class UserValidator: Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return User::class.java == clazz
    }

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty")
        val u = target as User
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        if (!passwordREGEX.matcher(u.password).matches()){
            errors.rejectValue("password", "Please follow password rules")
        }
    }
}


class UserSecurity
    (
    val id: String?,
    private val username: String,
    private val uPassword: String,
    private val uAuthorities: MutableCollection<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities() = uAuthorities
    override fun getPassword() = uPassword
    override fun getUsername() = username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired()= true
    override fun isEnabled() = true
}