package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

import hello.core.order.Order;
import hello.core.order.OrderService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();
        //OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService",MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService",OrderService.class);


        Member member = new Member(1L,"윤태민", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(1L,"크레파스",10000);

        System.out.println(order.toString());
        System.out.println(order.calculatePrice());
    }
}
