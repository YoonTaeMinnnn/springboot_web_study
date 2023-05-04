package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.helloTrace.HelloTraceV2;
import hello.advanced.trace.logTrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace logTrace;

    public void save(TraceStatus beforeStatus, String itemId) {

        TraceStatus status = logTrace.begin("OrderRepositoryV1.orderItem()");
        try {
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e; //예외를 잡지 말고 던져야 흐름을 유지할 수 있다.
        }
        logTrace.end(status);
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
