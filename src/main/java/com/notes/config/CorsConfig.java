package com.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// 跨域请求配置

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration(); // 添加cors配置信息
        config.addAllowedOrigin("http://localhost:3000"); // 具体的请求服务器
        config.setAllowCredentials(true); // 是否发送cookie信息
        config.addAllowedMethod("*"); // 允许的请求方式
        config.addAllowedHeader("*"); // 允许的头信息

        // 添加映射路径 拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);

        // 返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
