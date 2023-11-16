package top.lyuiowo.admin.common.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import top.lyuiowo.admin.common.model.User
import top.lyuiowo.admin.common.repository.UserRepository
import top.lyuiowo.admin.common.utils.ApiResponse
import java.security.SecureRandom
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
                val tokenSecret = generateTokenSecret();
                val token = generateToken(user.userID.toString(), tokenSecret);
                return listOf(
                    mapOf("token" to token)
                );
            }
        }
        return emptyList();
    }

    /**
     * 生成访问令牌
     * @param userID 用户ID
     * @param tokenSecret 令牌密钥
     * @return 访问令牌
     */
    private fun generateToken(userID: String, tokenSecret: String): String {
        val expirationTime = Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
            .setSubject(userID)
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, tokenSecret)
            .compact();
    }

    /**
     * 生成随机的令牌密钥
     * @return 令牌密钥
     */
    private fun generateTokenSecret(): String {
        val random = SecureRandom();
        val bytes = ByteArray(64);
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    companion object {
        private const val TOKEN_EXPIRATION_TIME = 86400000L; // 24小时
    }
}