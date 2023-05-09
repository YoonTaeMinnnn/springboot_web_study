package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("time proxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = invocation.proceed(); // target 실행

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("time proxy 종료 resultTime = {}", resultTime);

        return result;
    }

}
