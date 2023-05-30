package hello.proxy.app.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceV1ImplTest {

    @Autowired
    OrderServiceV1 orderServiceV1;

    @Test
    void test() {
        log.info("orderServiceV1 = {}", orderServiceV1.getClass());

    }

}