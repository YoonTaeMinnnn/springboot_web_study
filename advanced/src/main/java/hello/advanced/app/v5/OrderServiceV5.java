package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logTrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepositoryV5;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepositoryV5, LogTrace logTrace) {
        this.orderRepositoryV5 = orderRepositoryV5;
        this.template = new TraceTemplate(logTrace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", () -> {
            orderRepositoryV5.save(itemId);
            return null;
        });
    }

}
