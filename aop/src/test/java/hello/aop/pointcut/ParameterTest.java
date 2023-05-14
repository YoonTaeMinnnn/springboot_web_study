package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ParameterTest.ParameterAspect.class)
@Slf4j
@SpringBootTest
public class ParameterTest {


    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy = {}", memberService.getClass());
        memberService.hello("hello!!!");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect{

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {

        }


        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object args1 = joinPoint.getArgs()[0];
            log.info("logArgs1 = {}, args1 = {}", joinPoint.getSignature(), args1);
            return joinPoint.proceed();
        }


        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("logArgs2 = {}, args1 = {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // 훨씬 깔끔
        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg) throws Throwable {
            log.info("args1 = {}",arg);
        }

        // 스프링 컨테이너에 저장되는 프록시
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, Object obj) {
            log.info("[thisArgs] {}, object = {}", joinPoint.getSignature(), obj.getClass());
        }

        // 프록시 x, 실제 객체 (타겟)
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, Object obj) {
            log.info("[thisArgs] {}, object = {}", joinPoint.getSignature(), obj.getClass());
        }


    }

}
