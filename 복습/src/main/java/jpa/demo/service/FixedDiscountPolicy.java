package jpa.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
public class FixedDiscountPolicy implements DiscountPolicy {

    @Override
    public double discount(int price) {
        return price - 1000.0;
    }


}
