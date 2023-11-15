package top.lyuiowo.admin.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.lyuiowo.admin.common.model.User
import top.lyuiowo.admin.common.service.UserService
import top.lyuiowo.admin.common.utils.ApiResponse
import top.lyuiowo.admin.common.utils.ApiUtils.Companion.executeAndRespond
import java.util.UUID

@RestController
@RequestMapping("api/user")
class UserController(private val userService: UserService) {

    @GetMapping("/{userID}")
    fun getUserByUUID(
        @PathVariable userID: UUID
    ): ApiResponse<List<User>> {
//        val user = userService.findUserByID(userID)
//        return ApiResponse(202, "请求成功", user);
        return executeAndRespond {
            userService.findUserByID(userID);
        }
    }

    @PostMapping("/add")
    fun createUser(
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ApiResponse<List<User>> {
//        val newUser = userService.createUser(username, email, password);
//        return ApiResponse(202, "添加成功", newUser);
        return executeAndRespond {
            userService.createUser(username, email, password);
        }
    }

    @PostMapping("/updateUsername")
    fun updateUsername(
        @RequestParam userID: UUID,
        @RequestParam username: String
    ): ApiResponse<List<User>> {
//        val newUsername = userService.updateUsername(userID, username);
//        return ApiResponse(202, "修改成功", newUsername);
        return executeAndRespond {
            userService.updateUsername(userID, username);
        }
    }

    @PostMapping("/updateEmail")
    fun updateEmail(
        @RequestParam userID: UUID,
        @RequestParam email: String
    ): ApiResponse<List<User>> {
//        val newEmail = userService.updateEmail(userID, email);
//        return ApiResponse(202, "修改成功", newEmail);
        return executeAndRespond {
            userService.updateEmail(userID, email);
        }
    }

    @PostMapping("/remove")
    fun removeUser(
        @RequestParam userID: UUID
    ): ApiResponse<List<User>> {
//        val removeUser = userService.removeUser(userID);
//        return ApiResponse(202, "删除成功", removeUser);
        return executeAndRespond {
            userService.removeUser(userID);
        }
    }

    @PostMapping("/delete")
    fun deleteUser(
        @RequestParam userID: UUID
    ): ApiResponse<List<Unit>> {
//        val deleteUser = userService.deleteUser(userID);
//        return ApiResponse(202, "删除成功", deleteUser);
        return executeAndRespond {
            userService.deleteUser(userID);
        }
    }
}