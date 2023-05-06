package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping // 컴포넌트 스캔의 대상이 되지 않게 하기 위해서
@ResponseBody
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    @GetMapping("/v2/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderServiceV2.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
