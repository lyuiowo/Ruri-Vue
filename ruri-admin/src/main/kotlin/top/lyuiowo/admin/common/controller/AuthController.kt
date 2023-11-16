package top.lyuiowo.admin.common.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.common.service.AuthService
import top.lyuiowo.admin.common.utils.ApiResponse
import top.lyuiowo.admin.common.utils.ApiUtils.Companion.executeAndRespond

@RestController
@RequestMapping("api")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiResponse<List<Map<String, String>>> {
//        val user = authService.login(email, password);
//        return ApiResponse(200, "登录成功", user);
        return executeAndRespond {
            authService.login(email, password);
        }
    }
}