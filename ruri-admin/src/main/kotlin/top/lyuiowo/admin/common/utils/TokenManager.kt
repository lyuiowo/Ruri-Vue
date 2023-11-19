package top.lyuiowo.admin.common.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.security.SecureRandom
import java.util.*

class TokenManager {

    companion object {
        private const val TOKEN_EXPIRATION_TIME = 86400000L // 24小时
        private const val TOKEN_SECRET = "m4oFGWdePZ8NguVfeWk5jpUPwL8JO_KXRtgG9OohW-0" // 密钥


        /**
         * @param userID 用户 UUID
         * @return 临时访问令牌
         */
        fun generateToken(userID: UUID): String {
            val now = Date()
            val expiryDate = Date(now.time + TOKEN_EXPIRATION_TIME)
            val key = Keys.hmacShaKeyFor(TOKEN_SECRET.toByteArray())

            return Jwts.builder()
                .claim("userID", userID.toString())
                .expiration(expiryDate)
                .signWith(key)
                .compact()
        }

        /**
         * 根据访问令牌 (token) 获取当前用户的 UUID
         * @param token 访问令牌
         * @return 用户的 UUID
         */
        fun extractUserIDFromToken(token: String): UUID? {
            val claims: Claims = try {
                Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(TOKEN_SECRET.toByteArray()))
                    .build()
                    .parseClaimsJws(token)
                    .body
            } catch (e: Exception) {
                return null
            }

            val userID = claims["userID"] as String
            val expiration = claims.expiration

            if (expiration.before(Date())) {
                return null
            }

            return UUID.fromString(userID)
        }

        /**
         * @param length 密钥长度
         * @return 随机字符串的密钥
         */
        fun generateRandomTokenSecret(length: Int): String {
            val random = SecureRandom()
            val bytes = ByteArray(length)
            random.nextBytes(bytes)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
        }
    }
}