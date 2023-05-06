package hello.proxy.app.v2;

import org.springframework.stereotype.Repository;


public class OrderRepositoryV2 {

    public void save(String itemId) {
        if (itemId == null) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
