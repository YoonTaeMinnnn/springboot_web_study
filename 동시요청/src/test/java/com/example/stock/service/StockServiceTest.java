package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired private StockService stockService;
    @Autowired private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.save(new Stock(1L,100L));
    }

    @Test
    public void 재고감소() {
        //given & when
        stockService.decrease(1L, 1L);
        
        //then
        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(99);
    }

    @Test
    public void 동시에_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try{
                    stockService.decrease(1L, 1L);
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(0L);
    }

}