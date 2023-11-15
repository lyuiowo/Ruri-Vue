package top.lyuiowo.admin.common.utils

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val result: T?
)
