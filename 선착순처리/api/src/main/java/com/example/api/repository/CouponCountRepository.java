package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {

    private final RedisTemplate<String, String> template;

    public CouponCountRepository(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public Long increment() {
        return template
                .opsForValue()
                .increment("coupon_countrrrr");
    }

}
