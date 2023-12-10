package jpa.demo.service.discountpolicy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@MainOrderService
public class RateDiscountPolicy implements DiscountPolicy {

    @Override
    public double discount(int price) {
        return price * 0.9;
    }
}
