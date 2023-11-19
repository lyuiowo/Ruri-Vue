package top.lyuiowo.admin.common.utils

data class ApiManager<T>(
    val code: Int,
    val msg: String,
    val result: T?
)