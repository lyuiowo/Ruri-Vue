package top.lyuiowo.admin.controller

import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.model.User
import top.lyuiowo.admin.service.AuthService
import top.lyuiowo.admin.service.UserService
import top.lyuiowo.admin.utils.ApiManager

@RestController
@RequestMapping("api")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<List<Map<String, String>>> {
        return authService.login(email, password)
    }

    @PostMapping("/register")
    fun register(
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<List<User>> {
        return userService.createUser(username, email, password)
    }
}