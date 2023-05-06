package hello.proxy.config.v1_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v1_proxy.concreteproxy.OrderControllerConcreteProxy;
import hello.proxy.config.v1_proxy.concreteproxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1_proxy.concreteproxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public OrderControllerV2 orderController() {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderService());
        return new OrderControllerConcreteProxy(orderControllerV2, logTrace());
    }

    @Bean
    public OrderServiceV2 orderService() {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepository());
        return new OrderServiceConcreteProxy(orderServiceV2, logTrace());
    }

    @Bean
    public OrderRepositoryV2 orderRepository() {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepositoryV2, logTrace());
    }


}
