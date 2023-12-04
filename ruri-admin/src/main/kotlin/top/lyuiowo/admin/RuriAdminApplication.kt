package top.lyuiowo.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import top.lyuiowo.admin.config.WebConfig

@Import(WebConfig::class)
@SpringBootApplication
class RuriAdminApplication

fun main(args: Array<String>) {
    runApplication<RuriAdminApplication>(*args)
}
