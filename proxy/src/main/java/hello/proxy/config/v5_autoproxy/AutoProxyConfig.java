package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advise.LogTraceAdvise;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppV1Config.class, AppV2Config.class})
@Configuration
public class AutoProxyConfig {


    // aop 라이브러리 추가 -> BeanPostProcessor 는 자동으로 빈으로 등록되있음.
    // advisor 만 빈으로 등록하면 됨.
    // pointcut 조건이 하나(method)라도 만족 시, 프록시 생성
    // 하나도 만족 x -> 프록시 생성 x
    //@Bean
    public Advisor getAdvisor1(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request", "order*", "save*");
        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvise(logTrace));
    }

    // 원하는 위치와 원하는 메소드 명으로 pointcut 생성가능
    @Bean
    public Advisor getAdvisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvise(logTrace));
    }

}
