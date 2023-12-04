package top.lyuiowo.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedOrigin("http://localhost:5173") // 允许前端应用的域进行跨域访问
        config.addAllowedMethod("*") // 允许所有HTTP方法跨域访问
        config.addAllowedHeader("*") // 允许所有请求头跨域访问
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
