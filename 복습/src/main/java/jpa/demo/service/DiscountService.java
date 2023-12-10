package jpa.demo.service;

import jpa.demo.service.discountpolicy.DiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DiscountService {

    private final Map<String, DiscountPolicy> discountPolicyMap;
//    private final List<DiscountPolicy> discountPolicyList;

    public double discount(int price, String discountPolicy){
        DiscountPolicy discountPolicyResult = discountPolicyMap.get(discountPolicy);
        return discountPolicyResult.discount(price);
    }

}
