package top.lyuiowo.admin.common.utils

enum class ResultCode(val code: Int, val msg: String) {
    SUCCESS(200, "请求成功"),
    INVALID_USER(201, "用户不存在"),
    INVALID_EMAIL(202, "邮箱不存在"),
    PASSWORD_FAIL(203, "密码错误"),
    COMMON_FAIL(400, "请求错误")
}
