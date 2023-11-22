package top.lyuiowo.admin.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.repository.UserRepository
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager

@Service
class AuthService(private val userRepository: UserRepository) {

    /**
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    fun login(email: String, password: String): ApiManager<List<Map<String, String>>> {
        val user = userRepository.findByEmail(email)
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
                val token = TokenManager.generateToken(user.userID)
                return ApiManager(
                    ResultCode.SUCCESS.code,
                    "登录成功",
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
}