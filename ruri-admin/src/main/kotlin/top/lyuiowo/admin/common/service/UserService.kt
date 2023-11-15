package top.lyuiowo.admin.common.service

import org.springframework.stereotype.Service
import top.lyuiowo.admin.common.model.User
import top.lyuiowo.admin.common.repository.UserRepository
import java.sql.Timestamp
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {

    /**
     * @param userID 用户的 UUID
     * @return 用户信息
     */
    fun findUserByID(userID: UUID): List<User>? {
//        return userRepository.findByUserID(userID);
        val existingUser = userRepository.findByUserID(userID);
        if (existingUser != null) {
            if (existingUser.isDeleted) {
                throw RuntimeException("用户不存在");
            } else {
                return listOf(existingUser);
            }
        }
        return emptyList()
//        return if (existingUser?.isDeleted == false) listOf(existingUser) else emptyList();
    }

    /**
     * @param username 用户名
     * @param email 用户邮箱
     * @param password 密码
     * @return 用户保存信息
     */
    fun createUser(username: String, email: String, password: String): List<User> {
        val existingUserOfName = userRepository.findByUsername(username);
        val existingUserOfEmail = userRepository.findByEmail(email);
        if (existingUserOfName != null) {
            throw RuntimeException("用户名已存在");
        } else if (existingUserOfEmail != null) {
            throw RuntimeException("邮箱已存在");
        }

        val userID = UUID.randomUUID();
        val hashedPassword = User.hashPassword(password);
        val currentTime = Timestamp(System.currentTimeMillis());
        val newUser = User(
            userID = userID,
            username = username,
            email = email,
            password = hashedPassword,
            createAt = currentTime,
            lastJoinAt = currentTime);
        return listOf(userRepository.save(newUser));
    }

    /**
     * @param userID 用户的 UUID
     * @param newUsername 新用户名
     * @return
     */
    fun updateUsername(userID: UUID, newUsername: String): List<User>? {
        val existingUser = userRepository.findByUserID(userID);

        if (existingUser != null) {
            if (existingUser.isDeleted) {
                throw RuntimeException("找不到用户");
            } else {
                existingUser.username = newUsername;
                return listOf(userRepository.save(existingUser));
            }
        }
        return emptyList();
    }

    /**
     * @param userID 用户的 UUID
     * @param newEmail 新邮箱地址
     * @return
     */
    fun updateEmail(userID: UUID, newEmail: String): List<User>? {
        val existingUser = userRepository.findByUserID(userID);

        if (existingUser != null) {
            if (existingUser.isDeleted) {
                throw RuntimeException("用户不存在");
            } else {
                existingUser.email = newEmail;
                return listOf(userRepository.save(existingUser));
            }
        }
        return emptyList();
    }


    /**
     * @param userID 用户的 UUID
     * @return
     */
    fun removeUser(userID: UUID): List<User>? {
        val existingUser = userRepository.findByUserID(userID)
            ?: throw RuntimeException("用户不存在");

        if (existingUser.isDeleted) {
            return emptyList();
        }

        existingUser.isDeleted = true;
        return listOf(userRepository.save(existingUser));
    }

    /**
     * @param userID 用户 UUID
     * @return
     */
    fun deleteUser(userID: UUID): List<Unit>? {
        val existingUser = userRepository.findByUserID(userID);

        if (existingUser == null) {
            throw RuntimeException("用户不存在");
        } else {
            return listOf(userRepository.delete(existingUser));
        }
    }
}