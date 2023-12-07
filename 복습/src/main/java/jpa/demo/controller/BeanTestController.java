package jpa.demo.controller;

import jpa.demo.controller.annotation.MainOrderService;
import jpa.demo.service.DiscountPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bean")
@Slf4j
@RestController
public class BeanTestController {

    private final DiscountPolicy orderService;

    @Autowired
    public BeanTestController(@MainOrderService DiscountPolicy orderService) {
        this.orderService = orderService;
    }

//    @PostMapping("/primary")
//    public void test(){
//        log.info("OrderService = {}", orderService.order());
//    }
}
