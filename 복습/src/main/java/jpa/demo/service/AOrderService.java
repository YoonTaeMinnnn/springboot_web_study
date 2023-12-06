package jpa.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
public class AOrderService implements OrderService {

    @Override
    public String order() {
        return "A 주문 호출";
    }

    @Override
    public void cancel() {
        log.info("A 주문 취소 호출");
    }
}
