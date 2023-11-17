package top.lyuiowo.admin.common.utils

data class ApiManager<T>(
    val code: Int,
    val msg: String,
    val result: T?
) {
    companion object {
        fun <T> executeAndRespond(action: () -> List<T>?): ApiManager<List<T>> {
            return try {
                val result = action();
                ApiManager(200, "请求成功", result);
            } catch (e: Exception) {
                ApiManager(404, e.message ?: "请求失败", emptyList());
            }
        }
    }
}
