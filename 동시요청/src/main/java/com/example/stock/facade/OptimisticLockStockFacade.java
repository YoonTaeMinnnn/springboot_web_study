package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OptimisticLockStockFacade {
    private final StockService stockService;

    public OptimisticLockStockFacade(@Qualifier("o") StockService stockService) {
        this.stockService = stockService;
    }

    // version 이 달라서 실패할 경우, 재 시도를 위한 메소드
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true){
            try{
                stockService.decrease(id, quantity);
                break;
            }catch(Exception e){
                Thread.sleep(50);
            }
        }
    }
}
