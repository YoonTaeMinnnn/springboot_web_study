package jpa.demo.service.discountpolicy;


import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@Primary
public class FixedDiscountPolicy implements DiscountPolicy {

    @Override
    public double discount(int price) {
        return price - 1000.0;
    }


}
