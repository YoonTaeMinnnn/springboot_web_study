package com.example.service;

import com.example.domain.Coupon;
import com.example.repository.CouponCountRepository;
import com.example.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
    }

    public void apply(Long userId) {
//        long count = couponRepository.count();
        // redis 는 싱글 쓰레드 이므로, race condition 이 발생 하지 않는다.
        Long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
