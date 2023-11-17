package top.lyuiowo.admin.common.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.common.repository.UserRepository
import top.lyuiowo.admin.common.utils.TokenManager
import java.util.*

@Service
class AuthService(private val userRepository: UserRepository) {

    /**
     * @param username
     * @param password
     * @return
     */
    fun login(email: String, password: String): List<Map<String, String>>? {
        val user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.isDeleted) {
                throw RuntimeException("用户已被删除");
            }
            if (!user.verifyPassword(password)) {
                throw RuntimeException("密码错误");
            } else {
                val token = TokenManager.generateToken(user.userID);
                return listOf(
                    mapOf("token" to token)
                );
            }
        }
        return emptyList();
    }
}