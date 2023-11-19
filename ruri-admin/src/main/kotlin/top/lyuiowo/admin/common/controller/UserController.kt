package top.lyuiowo.admin.common.controller

import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.common.model.User
import top.lyuiowo.admin.common.service.UserService
import top.lyuiowo.admin.common.utils.ApiManager
import java.util.UUID

@RestController
@RequestMapping("api/user")
class UserController(private val userService: UserService) {

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
}