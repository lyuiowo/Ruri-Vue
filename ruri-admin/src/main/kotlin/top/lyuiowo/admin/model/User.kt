package top.lyuiowo.admin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class User (
    @Id
    val userID: UUID,
    var username: String,
    var avatar: String,
    var email: String,
    @get:JsonIgnore
    var password: String,
    val createAt: LocalDateTime,
    var lastJoinAt: LocalDateTime,
    var isAdmin: Boolean = false,
    @get:JsonIgnore
    var isDeleted: Boolean = false
) {
    companion object {

        /**
         * @param password 密码原文
         * @return 经过 hash 加密后的密码密文
         */
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt())
        }
    }

    /**
     * @param inputPassword 密码
     * @return 密码是否正确
     */
    fun verifyPassword(inputPassword: String): Boolean {
        return BCrypt.checkpw(inputPassword, this.password)
    }
}
