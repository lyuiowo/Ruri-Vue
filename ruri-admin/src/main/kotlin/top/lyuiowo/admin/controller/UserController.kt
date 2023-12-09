package top.lyuiowo.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.model.User
import top.lyuiowo.admin.service.UserService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.LoggerManager
import top.lyuiowo.admin.utils.ResultCode
import top.lyuiowo.admin.utils.TokenManager
import java.util.UUID

@RestController
@RequestMapping("api/user")
class UserController(
    private val userService: UserService
) {

    private val loggerManager = LoggerManager

    @GetMapping("/{userID}")
    fun getUserByUUID(
        request: HttpServletRequest,
        @PathVariable userID: UUID
    ): ApiManager<List<User>> {
        val existingUser = userService.findUserByID(userID)
        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }

    @PostMapping("/add")
    fun createUser(
        request: HttpServletRequest,
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<List<User>> {
        val existingUser = userService.createUser(username, email, password)
        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }

    @PostMapping("/update")
    fun updateUsername(
        request: HttpServletRequest,
        @RequestParam userID: UUID,
        @RequestParam username: String,
        @RequestParam email: String
    ): ApiManager<List<User>> {
        val existingUser = userService.updateUser(userID, username, email)
        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }

    @PostMapping("/remove")
    fun removeUser(
        request: HttpServletRequest,
        @RequestParam userID: UUID
    ): ApiManager<List<User>?> {
        val existingUser = userService.removeUser(userID)
        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }

    @PostMapping("/delete")
    fun deleteUser(
        request: HttpServletRequest,
        @RequestParam userID: UUID
    ): ApiManager<List<Unit>?> {
        val existingUser = userService.deleteUser(userID)
        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }

    @GetMapping("/searchUser")
    fun searchUser(
        request: HttpServletRequest,
        @RequestParam token: String
    ): ApiManager<List<User>>? {
        var existingUser: ApiManager<List<User>>?
        val userID = TokenManager.extractUserIDFromToken(token)
        existingUser = userID?.let { userService.findUserByID(it) }

        if (existingUser == null) {
            existingUser = ApiManager(ResultCode.INVALID_USER.code, ResultCode.INVALID_USER.msg, emptyList())
        }

        loggerManager.getLogger(request, existingUser.code)

        return existingUser
    }
}