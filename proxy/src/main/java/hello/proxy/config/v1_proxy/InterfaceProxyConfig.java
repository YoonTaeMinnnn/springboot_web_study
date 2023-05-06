package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController() {
        OrderControllerV1Impl orderControllerV1 = new OrderControllerV1Impl(orderService()); // 구체클래스 자체는 스프링빈 x
        return new OrderControllerInterfaceProxy(orderControllerV1, logTrace());
    }

    @Bean
    public OrderServiceV1 orderService() {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepository());
        return new OrderServiceInterfaceProxy(orderServiceV1, logTrace());
    }

    @Bean
    public OrderRepositoryV1 orderRepository() {
        OrderRepositoryV1Impl orderRepositoryV1 = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryV1, logTrace());
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }



}
