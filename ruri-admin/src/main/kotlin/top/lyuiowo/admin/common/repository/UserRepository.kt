package top.lyuiowo.admin.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import top.lyuiowo.admin.common.model.User
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByUserID(userID: UUID): User?;
    fun findByEmail(email: String): User?;
    fun findByUsername(username: String): User?;
}