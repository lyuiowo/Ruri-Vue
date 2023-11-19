package top.lyuiowo.admin.common.controller

import org.springframework.web.bind.annotation.*
import top.lyuiowo.admin.common.service.AuthService
import top.lyuiowo.admin.common.utils.ApiManager

@RestController
@RequestMapping("api")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiManager<List<Map<String, String>>> {
        return authService.login(email, password)
    }
}