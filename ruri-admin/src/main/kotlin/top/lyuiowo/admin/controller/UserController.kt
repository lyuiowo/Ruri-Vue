package top.lyuiowo.admin.controller

import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.model.User
import top.lyuiowo.admin.service.UserService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.util.UUID

@RestController
@RequestMapping("api/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userID}")
    fun getUserByUUID(
        @PathVariable userID: UUID
    ): ApiManager<List<User>> {
        return userService.findUserByID(userID)
    }

    @PostMapping("/add")
    fun createUser(
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<List<User>> {
        return userService.createUser(username, email, password)
    }

    @PostMapping("/updateUsername")
    fun updateUsername(
        @RequestParam userID: UUID,
        @RequestParam username: String
    ): ApiManager<List<User>> {
        return userService.updateUsername(userID, username)
    }

    @PostMapping("/updateEmail")
    fun updateEmail(
        @RequestParam userID: UUID,
        @RequestParam email: String
    ): ApiManager<List<User>> {
        return userService.updateEmail(userID, email)
    }

    @PostMapping("/remove")
    fun removeUser(
        @RequestParam userID: UUID
    ): ApiManager<List<User>?> {
        return userService.removeUser(userID)
    }

    @PostMapping("/delete")
    fun deleteUser(
        @RequestParam userID: UUID
    ): ApiManager<List<Unit>?> {
        return userService.deleteUser(userID)
    }

    @GetMapping("/searchUser")
    fun searchUser(
        @RequestParam token: String
    ): ApiManager<List<User>> {
        val userID = TokenManager.extractUserIDFromToken(token)
            ?: return ApiManager(
                ResultCode.INVALID_USER.code,
                ResultCode.INVALID_USER.msg,
                emptyList()
            )
        return userService.findUserByID(userID)
    }
}