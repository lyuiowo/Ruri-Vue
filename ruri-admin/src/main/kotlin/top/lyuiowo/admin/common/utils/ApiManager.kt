package top.lyuiowo.admin.common.utils

import top.lyuiowo.admin.common.model.User

data class ApiManager<T>(
    val code: Int,
    val msg: String,
    val result: T?
)