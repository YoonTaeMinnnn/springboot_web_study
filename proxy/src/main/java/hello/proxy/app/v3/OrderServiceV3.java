package hello.proxy.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV3;

    public void orderItem(String itemId) {
        orderRepositoryV3.save(itemId);
    }

}
