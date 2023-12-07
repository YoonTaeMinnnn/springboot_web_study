package jpa.demo.service;

import jpa.demo.controller.annotation.MainOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MainOrderService
public class RateDiscountPolicy implements DiscountPolicy {

    @Override
    public double discount(int price) {
        return price * 0.9;
    }
}
