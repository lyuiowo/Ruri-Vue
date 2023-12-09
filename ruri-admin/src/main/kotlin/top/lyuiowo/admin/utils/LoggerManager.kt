package top.lyuiowo.admin.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoggerManager {
    companion object {

        private val logger = KotlinLogging.logger { }
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        private val ResetColor = "\u001B[0m"
        private val ErrorColor = "\u001B[31m"
        private val SuccessColor = "\u001B[32m"

        fun getLogger(
            request: HttpServletRequest,
            code: Int
        ) {
            val method = request.method
            val url = URL(request.requestURL.toString()).path
            val currentTime = dateTimeFormatter.format(LocalDateTime.now())
            val codeStatus = if (code == 200) "$SuccessColor$code$ResetColor" else "$ErrorColor$code$ResetColor"

            logger.info { "$method $url $codeStatus $currentTime" }
        }
    }
}