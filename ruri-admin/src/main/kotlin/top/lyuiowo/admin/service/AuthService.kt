package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.repository.UserRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.time.LocalDateTime
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    /**
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    fun login(email: String, password: String): ApiManager<List<Map<String, String>>> {
        val user = userRepository.findByEmail(email)
        val currentTime = LocalDateTime.now()
        if (user != null) {
            if (user.isDeleted) {
                return ApiManager(
                    ResultCode.INVALID_USER.code,
                    ResultCode.INVALID_USER.msg,
                    emptyList()
                )
            }
            if (!user.verifyPassword(password)) {
                return ApiManager(
                    ResultCode.PASSWORD_FAIL.code,
                    ResultCode.PASSWORD_FAIL.msg,
                    emptyList()
                )
            } else {
                user.lastJoinAt = currentTime
                userRepository.save(user)

                val token = TokenManager.generateToken(user.userID)
                return ApiManager(
                    ResultCode.SUCCESS.code,
                    "欢迎回来" + user.username,
                    listOf(
                        mapOf("token" to token)
                    )
                )
            }
        }
        return ApiManager(
            ResultCode.INVALID_USER.code,
            ResultCode.INVALID_USER.msg,
            emptyList()
        )
    }

    fun register(username: String, email: String, password: String): ApiManager<Map<String, String>> {
        val user = userService.createUser(username, email, password);
        var userID: UUID? = null
        if (user.code == 200 && user.result?.isNotEmpty() == true) {
            userID = user.result[0].userID;
            val token = TokenManager.generateToken(userID)
            return ApiManager(
                ResultCode.SUCCESS.code,
                "注册成功",
                mapOf("token" to token)
            )
        } else {
            return ApiManager(
                user.code,
                user.msg,
                emptyMap()
            )
        }
    }
}