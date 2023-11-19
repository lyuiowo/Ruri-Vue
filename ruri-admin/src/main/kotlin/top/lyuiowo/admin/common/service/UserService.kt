package top.lyuiowo.admin.common.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.common.model.User
import top.lyuiowo.admin.common.repository.UserRepository
import top.lyuiowo.admin.common.utils.ApiManager
import top.lyuiowo.admin.common.utils.ResultCode
import java.sql.Timestamp
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {

    /**
     * @param userID 用户的 UUID
     * @return 用户信息
     */
    fun findUserByID(userID: UUID): ApiManager<List<User>> {
        val existingUser = userRepository.findByUserID(userID)
        if (existingUser != null) {
            return if (existingUser.isDeleted) {
                ApiManager(
                    ResultCode.INVALID_USER.code,
                    ResultCode.INVALID_USER.msg,
                    emptyList()
                )
            } else {
                ApiManager(
                    ResultCode.SUCCESS.code,
                    "查询成功",
                    listOf(existingUser)
                )
            }
        }
        return ApiManager(
            ResultCode.INVALID_USER.code,
            ResultCode.INVALID_USER.msg,
            emptyList()
        )
    }

    /**
     * @param username 用户名
     * @param email 用户邮箱
     * @param password 密码
     * @return 用户保存信息
     */
    fun createUser(username: String, email: String, password: String): ApiManager<List<User>> {
        val existingUserOfName = userRepository.findByUsername(username)
        val existingUserOfEmail = userRepository.findByEmail(email)
        if (existingUserOfName != null) {
            return ApiManager(
                ResultCode.COMMON_FAIL.code,
                "用户名已注册",
                emptyList()
            )
        } else if (existingUserOfEmail != null) {
            return ApiManager(
                ResultCode.COMMON_FAIL.code,
                "邮箱已注册",
                emptyList()
            )
        }

        val userID = UUID.randomUUID()
        val hashedPassword = User.hashPassword(password)
        val currentTime = Timestamp(System.currentTimeMillis())
        val newUser = User(
            userID = userID,
            username = username,
            email = email,
            password = hashedPassword,
            createAt = currentTime,
            lastJoinAt = currentTime)
        return ApiManager(
            ResultCode.SUCCESS.code,
            "创建成功",
            listOf(userRepository.save(newUser))
        )
    }

    /**
     * @param userID 用户的 UUID
     * @param newUsername 新用户名
     * @return
     */
    fun updateUsername(userID: UUID, newUsername: String): ApiManager<List<User>> {
        val existingUser = userRepository.findByUserID(userID)

        return updateUser(existingUser, newUsername)
    }

    /**
     * @param userID 用户的 UUID
     * @param newEmail 新邮箱地址
     * @return
     */
    fun updateEmail(userID: UUID, newEmail: String): ApiManager<List<User>> {
        val existingUser = userRepository.findByUserID(userID)

        return updateUser(existingUser, newEmail)
    }


    /**
     * @param userID 用户的 UUID
     * @return
     */
    fun removeUser(userID: UUID): ApiManager<List<User>?> {
        val existingUser = userRepository.findByUserID(userID)
            ?: return ApiManager(
                ResultCode.INVALID_USER.code,
                ResultCode.INVALID_USER.msg,
                emptyList()
            )

        if (existingUser.isDeleted) {
            return ApiManager(
                ResultCode.INVALID_USER.code,
                ResultCode.INVALID_USER.msg,
                emptyList()
            )
        }

        existingUser.isDeleted = true
        return ApiManager(
            ResultCode.SUCCESS.code,
            "删除成功",
            listOf(userRepository.save(existingUser)))
        
    }

    /**
     * @param userID 用户 UUID
     * @return
     */
    fun deleteUser(userID: UUID): ApiManager<List<Unit>?> {
        val existingUser = userRepository.findByUserID(userID)

        return if (existingUser == null) {
            ApiManager(
                ResultCode.INVALID_USER.code,
                ResultCode.INVALID_USER.msg,
                emptyList())
        } else {
            ApiManager(
                ResultCode.SUCCESS.code,
                "删除成功",
                listOf(userRepository.delete(existingUser))
            )
        }
    }

    /**
     * 更新
     */
    fun updateUser(user: User?, newValue: String): ApiManager<List<User>> {
        if (user != null) {
            return if (user.isDeleted) {
                ApiManager(
                    ResultCode.INVALID_USER.code,
                    ResultCode.INVALID_USER.msg,
                    emptyList()
                )
            } else {
                user.username = newValue
                ApiManager(
                    ResultCode.SUCCESS.code,
                    "更新成功",
                    listOf(userRepository.save(user))
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