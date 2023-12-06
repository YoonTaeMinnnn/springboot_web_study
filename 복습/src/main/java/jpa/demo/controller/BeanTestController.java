package jpa.demo.controller;

import jpa.demo.controller.annotation.MainOrderService;
import jpa.demo.domain.item.Book;
import jpa.demo.domain.item.Movie;
import jpa.demo.repository.BookRepository;
import jpa.demo.repository.ItemRepository;
import jpa.demo.repository.MovieRepository;
import jpa.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bean")
@Slf4j
@RestController
public class BeanTestController {

    private final OrderService orderService;

    @Autowired
    public BeanTestController(@MainOrderService OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/primary")
    public void test(){
        log.info("OrderService = {}", orderService.order());
    }
}
