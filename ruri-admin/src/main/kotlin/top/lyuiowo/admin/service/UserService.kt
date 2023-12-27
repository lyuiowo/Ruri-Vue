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
class UserService(
    private val userRepository: UserRepository
) {

    private val notUser = "00000000-0000-0000-0000-000000000000"

    /**
     * 通过用户唯一标识查询用户信息
     * @param userID 用户的 UUID
     * @return 用户信息
     */
    fun findUserByID(userID: UUID): ApiManager<List<User>> {
        val existingUser = userRepository.findByUserID(userID)

        if (existingUser != null) {
            return if (existingUser.isDeleted) {
                ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null)
            } else {
                ApiManager(ResultCode.SUCCESS.code, "查询成功", listOf(existingUser))
            }
        }
        return ApiManager(
            ResultCode.COMMON_FAIL.code, ResultCode.COMMON_FAIL.msg, null
        )
    }

    /**
     * 创建新用户
     * @param username 用户名
     * @param email 用户邮箱
     * @param password 密码
     * @return 保存结果
     */
    fun createUser(username: String, email: String, password: String): ApiManager<List<User>> {
        val existingUserOfName = userRepository.findByUsername(username)
        val existingUserOfEmail = userRepository.findByEmail(email)
        if (existingUserOfName != null) {
            return ApiManager(ResultCode.COMMON_FAIL.code, "用户名已注册", null)
        } else if (existingUserOfEmail != null) {
            return ApiManager(ResultCode.COMMON_FAIL.code, "邮箱已注册", null)
        }

        val userID = UUID.randomUUID()
        val hashedPassword = User.hashPassword(password)
        val currentTime = LocalDateTime.now()
        val newUser = User(
            userID = userID,
            avatar = "",
            username = username,
            email = email,
            password = hashedPassword,
            createAt = currentTime,
            lastJoinAt = currentTime
        )
        userRepository.save(newUser)
        return ApiManager(ResultCode.SUCCESS.code, "创建成功", listOf(newUser))
    }

    /**
     * 删除用户 - 逻辑删除
     * @param userID 用户的 UUID
     * @return 删除结果
     */
    fun removeUser(userID: UUID): ApiManager<List<User>?> {
        val existingUser = userRepository.findByUserID(userID) ?: return ApiManager(
            ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null
        )

        if (existingUser.isDeleted) {
            return ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null)
        }

        existingUser.isDeleted = true
        userRepository.save(existingUser)

        return ApiManager(ResultCode.SUCCESS.code, "删除成功", listOf(existingUser))
    }

    /**
     * 删除用户 - 物理删除
     * @param userID 用户 UUID
     * @return 删除结果
     */
    fun deleteUser(userID: UUID): ApiManager<List<User>?> {
        val existingUser = userRepository.findByUserID(userID)

        return if (existingUser == null) {
            ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null)
        } else {
            userRepository.delete(existingUser)
            ApiManager(ResultCode.SUCCESS.code, "删除成功", listOf(existingUser))
        }
    }

    /**
     * 更新用户信息
     * @param userID 用户唯一标识
     * @param username 用户名
     * @param email 邮箱
     * @return 更新结果
     */
    fun updateUser(token: String, username: String, email: String): ApiManager<List<User>> {
        val userID = TokenManager.extractUserIDFromToken(token) ?: UUID.fromString(notUser)
        val user = userRepository.findByUserID(userID)
        if (user != null) {
            return if (user.isDeleted) {
                ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null)
            } else {
                user.username = username
                user.email = email
                userRepository.save(user)
                ApiManager(ResultCode.SUCCESS.code, "更新成功", listOf(user))
            }
        }
        return ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, null)
    }
}