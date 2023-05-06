package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class OrderServiceV2 {
    private final OrderRepositoryV2 orderRepositoryV2;

    public void orderItem(String itemId) {
        orderRepositoryV2.save(itemId);
    }

}
