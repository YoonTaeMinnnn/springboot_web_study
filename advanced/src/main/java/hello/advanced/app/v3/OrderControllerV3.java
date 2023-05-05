package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.helloTrace.HelloTraceV2;
import hello.advanced.trace.logTrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderServiceV3;
    private final LogTrace logTrace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = logTrace.begin("OrderController.request()");
        try {
            orderServiceV3.orderItem(itemId);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e; //예외를 잡지 말고 던져야 흐름을 유지할 수 있다.
        }
        logTrace.end(status);
        return "ok";
    }

}
