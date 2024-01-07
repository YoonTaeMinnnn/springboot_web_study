package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId) {
//        long count = couponRepository.count();
        // redis 는 싱글 쓰레드 이므로, race condition 이 발생 하지 않는다.
        Long count = couponCountRepository.increment();
        System.out.println("count = " + count);
        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
//        couponCreateProducer.create(userId);
    }
}
