package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {

    private final LogTrace logTrace;
    private final Object target;
    private final String[] pattern;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        // 메소드 이름 필터링
        if (!PatternMatchUtils.simpleMatch(pattern, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
        try {
            status = logTrace.begin(message);
            Object result = method.invoke(target, args);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
