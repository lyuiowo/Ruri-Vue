package top.lyuiowo.admin.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.common.service.AuthService
import top.lyuiowo.admin.common.utils.ApiManager
import top.lyuiowo.admin.common.utils.TokenManager
import java.util.UUID

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
        return authService.login(email, password);
    }

    @GetMapping("/testTokenGet")
    fun testTokenGet(
        @RequestParam token: String
    ): UUID? {
        return TokenManager.extractUserIDFromToken(token);
    }
}