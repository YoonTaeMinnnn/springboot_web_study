package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.helloTrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderServiceV1;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {
        TraceStatus status = trace.begin("OrderController.request()");
        try {
            orderServiceV1.orderItem(itemId);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 잡지 말고 던져야 흐름을 유지할 수 있다.
        }
        trace.end(status);
        return "ok";
    }

}
