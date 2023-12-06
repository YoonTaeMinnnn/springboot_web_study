package jpa.demo.service;

import jpa.demo.controller.annotation.MainOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MainOrderService
public class BOrderService implements OrderService {
    @Override
    public String order() {
        return "B 주문 호출";
    }

    @Override
    public void cancel() {
        log.info("B 주문 취소 호출");
    }
}
