package login.jwtlogin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration(); //json을 자바스크립트에서 사용할 수 있도록 설정
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");  //응답 허용 (모든 ip에)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");  //get, post, put, delete 에 응답 모두 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
