package hello.proxy.app.v1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerV1Impl implements OrderControllerV1{

    private final OrderServiceV1 orderServiceV1;

    @Override
    public String request(String itemId) {
        orderServiceV1.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
