package jpa.demo.service.discountpolicy;

import jpa.demo.service.discountpolicy.DiscountPolicy;
import jpa.demo.service.discountpolicy.FixedDiscountPolicy;
import jpa.demo.service.discountpolicy.RateDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 다형성을 활용하는 비즈니스 로직에 대해서는 수동 빈 등록을 권장
// 빈의 위치를 한눈에 파악하기 좋다
@Configuration
public class DiscountConfig {

    @Bean
    public DiscountPolicy fixedDiscountPolicy() {
        return new FixedDiscountPolicy();
    }

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
