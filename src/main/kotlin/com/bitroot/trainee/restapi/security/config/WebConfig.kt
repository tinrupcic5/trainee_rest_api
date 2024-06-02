package com.bitroot.trainee.restapi.security.config

import com.bitroot.trainee.restapi.security.filter.AuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(val authFilter: AuthFilter) : WebMvcConfigurer {
//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/Users/tinrupcic/Development/myStuff/puravida/backend/trainee-rest-api/src/main/resources")
//    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authFilter)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/resources/**",
                "/error",
                "/api/user/login",
                "/api/version",
                "/api/user/guest",
                "/sign-up",
            ) // Exclude these paths
    }

    // need this for frontend calls
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("*") // TODO Replace with the actual origin of your Flutter app
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .exposedHeaders("Authorization")
                    .allowCredentials(true)
            }
        }
    }
}
