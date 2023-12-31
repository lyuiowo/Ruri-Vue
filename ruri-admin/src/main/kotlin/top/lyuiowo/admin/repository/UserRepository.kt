package top.lyuiowo.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import top.lyuiowo.admin.model.User
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByUserID(userID: UUID): User?
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
}