package top.lyuiowo.admin.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.model.User
import top.lyuiowo.admin.service.AuthService
import top.lyuiowo.admin.service.UserService
import top.lyuiowo.admin.utils.ApiManager
import top.lyuiowo.admin.utils.LoggerManager
import java.util.UUID

@RestController
@RequestMapping("api")
class AuthController(
    private val authService: AuthService
) {

    private val loggerManager = LoggerManager

    @PostMapping("/login")
    fun login(
        request: HttpServletRequest,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<Map<String, String>> {
        val auth = authService.login( email, password )
        loggerManager.getLogger( request, auth.code )

        return auth
    }

    @PostMapping("/register")
    fun register(
        request: HttpServletRequest,
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<Map<String, String>> {
        val auth = authService.register( username, email, password )
        loggerManager.getLogger( request, auth.code )

        return auth
    }

    @PostMapping("/change")
    fun change(
        request: HttpServletRequest,
        @RequestParam token: String,
        @RequestParam oldPassword: String,
        @RequestParam newPassword: String
    ): ApiManager<User?> {
        val auth = authService.changePassword(token, oldPassword, newPassword)
        loggerManager.getLogger(request, auth.code)

        return auth
    }
}