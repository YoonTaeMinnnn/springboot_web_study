package com.example.api.service;


import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired private ApplyService applyService;
    @Autowired private CouponRepository couponRepository;
    @Autowired private CouponCountRepository couponCountRepository;

    @Test
    public void 한번만응모() {
        applyService.apply(1L);

        long count = couponRepository.count();
//        Long count = couponCountRepository.increment();


        assertThat(count).isEqualTo(1);
    }


    @Test
    public void 여러명응모() throws InterruptedException {

        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // js의 await와 같은 기능 -> 비동기처리
        // 정해진 쓰레드 갯수만큼 실행이 끝날 때까지 대기
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(()->{
                try{
                    applyService.apply(userId);
                    System.out.println("userId = " + userId);
                } finally {
                    latch.countDown();
                }
            });
        }

        // count 가 0이 될 때까지 대기
        latch.await();


        // when
        long count = couponRepository.count();

        //then
        assertThat(count).isEqualTo(100);

    }


    @Test
    public void 한명당_한개의쿠폰만_발급() throws InterruptedException {

        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // js의 await와 같은 기능 -> 비동기처리
        // 정해진 쓰레드 갯수만큼 실행이 끝날 때까지 대기
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try{
                    applyService.apply(1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        // count 가 0이 될 때까지 대기
        latch.await();


        // when
        long count = couponRepository.count();

        //then
        assertThat(count).isEqualTo(1);

    }
}