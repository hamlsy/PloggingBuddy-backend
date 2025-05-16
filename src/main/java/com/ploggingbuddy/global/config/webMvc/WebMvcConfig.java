package com.ploggingbuddy.global.config.webMvc;

import com.ploggingbuddy.security.resolver.CustomAuthenticationPrincipalArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    //todo 환경변수 파일로
    private static final String CORS_FRONT_PATH = "http://localhost:3000";
    private final CustomAuthenticationPrincipalArgumentResolver customAuthenticationPrincipalArgumentResolver;

    //resolver
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customAuthenticationPrincipalArgumentResolver);
    }

    //CORS setting
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns(CORS_FRONT_PATH)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}