package hello.advanced.trace.threadLocal;

import hello.advanced.trace.threadLocal.code.FieldService;
import hello.advanced.trace.threadLocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService threadLocalService = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");
        //쓰레드 생성
        Runnable userA = () -> {
            threadLocalService.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalService.logic("userB");
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

