package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.helloTrace.HelloTraceV2;
import hello.advanced.trace.logTrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV3;
    private final LogTrace logTrace;

    public void orderItem(TraceStatus beforeStatus, String itemId) {
        TraceStatus status = logTrace.begin("OrderServiceV1.orderItem()");
        try {
            orderRepositoryV3.save(status, itemId);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e; //예외를 잡지 말고 던져야 흐름을 유지할 수 있다.
        }
        logTrace.end(status);
    }

}
