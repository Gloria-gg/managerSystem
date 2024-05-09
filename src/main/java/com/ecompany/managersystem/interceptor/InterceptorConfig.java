package com.ecompany.managersystem.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Ge
 * @Create: 2024-05-08 14:38
 * @Description: register interceptor
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public RoleInterceptor roleInterceptor() {
        return new RoleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor())
                // all urls should be checked
                .addPathPatterns("/**");
    }
}
