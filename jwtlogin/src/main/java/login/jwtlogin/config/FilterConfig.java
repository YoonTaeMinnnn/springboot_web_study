package login.jwtlogin.config;

import login.jwtlogin.filter.MyFilterV1;
import login.jwtlogin.filter.MyFilterV2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean myFilterV1() {
        FilterRegistrationBean<MyFilterV1> filterV1FilterRegistrationBean = new FilterRegistrationBean<>(new MyFilterV1());
        filterV1FilterRegistrationBean.addUrlPatterns("/*");
        filterV1FilterRegistrationBean.setOrder(0);
        return filterV1FilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilterV2() {
        FilterRegistrationBean<MyFilterV2> filterV1FilterRegistrationBean = new FilterRegistrationBean<>(new MyFilterV2());
        filterV1FilterRegistrationBean.addUrlPatterns("/*");
        filterV1FilterRegistrationBean.setOrder(1);
        return filterV1FilterRegistrationBean;
    }

}
