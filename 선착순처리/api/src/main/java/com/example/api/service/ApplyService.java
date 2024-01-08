package com.example.api.service;

import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.AppliedUserRepository;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        // 유저 한명 당, 하나의 쿠폰만 발급 가능
        Long apply = appliedUserRepository.add(userId);

        // 1 반환 시, 중복x
        if (apply != 1) {
            return;
        }

//        long count = couponRepository.count();
        // redis 는 싱글 쓰레드 이므로, race condition 이 발생 하지 않는다.
        Long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

//        couponRepository.save(new Coupon(userId));
        couponCreateProducer.create(userId);
    }
}
