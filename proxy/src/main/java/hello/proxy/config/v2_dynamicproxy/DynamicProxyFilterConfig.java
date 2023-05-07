package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {
    private final static String[] PATTERNS = {"request", "order*", "save*"};

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public OrderRepositoryV1 orderRepository() {
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();
        LogTraceFilterHandler handler = new LogTraceFilterHandler(logTrace(), target, PATTERNS);
        OrderRepositoryV1 proxy = (OrderRepositoryV1)
                Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler);
        return proxy;
    }

    @Bean
    public OrderServiceV1 orderService() {
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepository());
        LogTraceFilterHandler handler = new LogTraceFilterHandler(logTrace(), target, PATTERNS);
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler);
        return proxy;
    }

    @Bean
    public OrderControllerV1 orderController() {
        OrderControllerV1 target = new OrderControllerV1Impl(orderService());
        LogTraceFilterHandler handler = new LogTraceFilterHandler(logTrace(), target, PATTERNS);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler);
        return proxy;
    }
}
