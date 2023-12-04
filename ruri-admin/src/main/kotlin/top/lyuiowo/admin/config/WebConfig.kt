package top.lyuiowo.admin.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val converter = MappingJackson2HttpMessageConverter()
//        converter.objectMapper = ObjectMapper().apply {
//            // 配置你的 ObjectMapper，例如日期格式化等
//        }
        converters.add(converter)
    }
}