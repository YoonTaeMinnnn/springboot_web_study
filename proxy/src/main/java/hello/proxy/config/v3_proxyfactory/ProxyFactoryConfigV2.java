package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advise.LogTraceAdvise;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public OrderControllerV2 orderController() {
        OrderControllerV2 target = new OrderControllerV2(orderService());
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor());
        OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();
        return proxy;
    }

    @Bean
    public OrderServiceV2 orderService() {
        OrderServiceV2 target = new OrderServiceV2(orderRepository());
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor());
        OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();
        return proxy;
    }



    @Bean
    public OrderRepositoryV2 orderRepository() {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor());
        OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();
        return proxy;
    }

    private Advisor getAdvisor() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request", "order*", "save*");
        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvise(logTrace()));
    }

}
