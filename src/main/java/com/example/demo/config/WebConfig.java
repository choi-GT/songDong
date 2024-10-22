package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private final SessionInterceptor sessionInterceptor;
	
    public WebConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    //기영씨가 작업한거 < 시작 >
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // CORS를 적용할 URL 패턴
                .allowedOrigins("http://localhost:8080") // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true); // 인증 정보 포함 여부
    }
    
    // 기영씨가 작업한거 < 끝 >
    
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /admin/** 경로에 대해 인터셉터 적용
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/admin/**") // 관리자 페이지 경로
                .addPathPatterns("/member/updateMemberInfo") // 필요한 다른 경로 추가
                .addPathPatterns("/member/myPage")
                .addPathPatterns("/member/favoriteList");
}
}
