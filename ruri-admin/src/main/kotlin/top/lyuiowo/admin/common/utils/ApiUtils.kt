package top.lyuiowo.admin.common.utils

class ApiUtils {
    companion object {
        fun <T> executeAndRespond(action: () -> List<T>?): ApiResponse<List<T>> {
            return try {
                val result = action();
                ApiResponse(200, "请求成功", result);
            } catch (e: Exception) {
                ApiResponse(404, e.message ?: "请求失败", emptyList());
            }
        }
    }
}