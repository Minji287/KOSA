package com.example.unipet.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      //CORS 설정을 담는 구성 소스입니다.
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      // 실제 CORS 구성을 담고 있는 객체입니다. 여기서는 모든 origin, 헤더, 메서드를 허용하는 간단한 설정이 이루어져 있습니다.
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true); // 인증 정보를 허용하도록 설정합니다.
      config.addAllowedOrigin("http://localhost:5173"); // 모든 origin(도메인)을 허용합니다.
      config.addAllowedHeader("*"); // 모든 헤더를 허용합니다.
      config.addAllowedMethod("*"); // 모든 HTTP 메서드를 허용합니다.
      config.addExposedHeader("Authorization");
      // "/api/" 패턴의 URL에 대해서만 위에서 설정한 CORS 구성을 적용하도록 등록합니다.
      source.registerCorsConfiguration("/**", config);
      // 설정한 source를 사용하여 CorsFilter 객체를 생성하고 반환합니다.
      return new CorsFilter(source);
   }
}