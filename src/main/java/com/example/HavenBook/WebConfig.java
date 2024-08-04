package com.example.HavenBook;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS global para a aplicação Spring Boot.
 * Define as políticas de compartilhamento de recursos entre origens.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adiciona mapeamentos de CORS para a aplicação.
     *
     * @param registry O registro de CORS para configurar as políticas.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "https://havenbook.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
