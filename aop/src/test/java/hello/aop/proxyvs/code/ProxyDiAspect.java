package hello.aop.proxyvs.code;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.condition.Join;

@Slf4j
@Aspect
public class ProxyDiAspect {

    @Before("execution(* hello.aop..*.*(..))")
    public void doTrace(JoinPoint joinPoint) {
        log.info("[proxyDiAdvise] {}", joinPoint.getSignature());

    }
}
