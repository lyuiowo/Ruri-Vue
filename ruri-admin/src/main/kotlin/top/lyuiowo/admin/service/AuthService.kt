package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.model.User
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

    val notUser = "00000000-0000-0000-0000-000000000000"

    /**
     * 登录操作
     * @param email 邮箱
     * @param password 密码
     * @return 登录结果
     */
    fun login(email: String, password: String): ApiManager<Map<String, String>> {
        val user = userRepository.findByEmail(email)
        val currentTime = LocalDateTime.now()
        if (user != null) {
            if (user.isDeleted) {
                return ApiManager(
                    ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, emptyMap()
                )
            }
            return if (!user.verifyPassword(password)) {
                ApiManager(
                    ResultCode.PASSWORD_FAIL.code, ResultCode.PASSWORD_FAIL.msg, emptyMap()
                )
            } else {
                user.lastJoinAt = currentTime
                userRepository.save(user)

                val token = TokenManager.generateToken(user.userID)
                ApiManager(
                    ResultCode.SUCCESS.code, "欢迎回来" + user.username, mapOf("token" to token)
                )
            }
        }
        return ApiManager(
            ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, emptyMap()
        )
    }

    /**
     * 注册新用户
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return 注册结果
     */
    fun register(username: String, email: String, password: String): ApiManager<Map<String, String>> {
        val user = userService.createUser(username, email, password);
        val userID: UUID?
        return if (user.code == 200 && user.result?.isNotEmpty() == true) {
            userID = user.result[0].userID;
            val token = TokenManager.generateToken(userID)
            ApiManager(
                ResultCode.SUCCESS.code, "注册成功", mapOf("token" to token)
            )
        } else {
            ApiManager(
                user.code, user.msg, emptyMap()
            )
        }
    }

    fun changePassword(token: String, oldPassword: String, newPassword: String): ApiManager<User?> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val user = userRepository.findByUserID(userID)

        if (user != null) {
            return if (user.verifyPassword(oldPassword)) {
                if (user.verifyPassword(newPassword)) {
                    ApiManager(ResultCode.COMMON_FAIL.code, "新密码不能和旧密码相同", null)
                } else {
                    user.password = User.hashPassword(newPassword)
                    userRepository.save(user)
                    ApiManager(ResultCode.SUCCESS.code, "密码修改成功", null)
                }
            } else {
                ApiManager(ResultCode.COMMON_FAIL.code, "密码错误", null)
            }
        }

        return ApiManager(ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, null)
    }
}