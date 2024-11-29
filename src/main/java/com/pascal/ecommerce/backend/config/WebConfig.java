package com.pascal.ecommerce.backend.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigin = System.getProperty("CROSS_ORIGIN", "*"); // Valor por defecto si no está configurado
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
