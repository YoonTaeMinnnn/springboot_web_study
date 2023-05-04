package hello.advanced.trace.threadLocal;

import hello.advanced.trace.threadLocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main start");
        //쓰레드 생성
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("Thread -A");

        Thread threadB = new Thread(userB);
        threadB.setName("Thread-B");

        threadA.start();
        sleep(100);

        threadB.start();

        sleep(3000); // 메인 쓰레드 대기
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

