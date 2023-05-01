package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.helloTrace.HelloTraceV1;
import hello.advanced.trace.helloTrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;
    private final HelloTraceV2 trace;

    public void orderItem(TraceStatus beforeStatus, String itemId) {
        TraceStatus status = trace.beginSync(beforeStatus.getTraceId(),"OrderServiceV1.orderItem()");
        try {
            orderRepositoryV2.save(status, itemId);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 잡지 말고 던져야 흐름을 유지할 수 있다.
        }
        trace.end(status);
    }

}
