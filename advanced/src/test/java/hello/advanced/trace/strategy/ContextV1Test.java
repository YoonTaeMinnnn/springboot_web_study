package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    /*
    변하는 부분 : 비즈니스 로직
    변하지 않는 부분 : 시간 측정 로직
     */
    private void logic1() {
        // 비즈니스 로직 시작
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직 1 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    private void logic2() {
        // 비즈니스 로직 시작
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직 2 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        contextV2.execute();
    }

    // 익명 내부 클래스 사용
    @Test
    void strategyV2() {
        // 인터페이스에 메소드 1개인 경우 람다로 변경가능
        ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직 1 실행"));
        contextV1.execute();

        ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직 2 실행"));
        contextV2.execute();
    }

    @Test
    void strategyV3() {
        // 인터페이스에 메소드 1개인 경우 람다로 변경가능
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직 1 실행"));
        context.execute(() -> log.info("비즈니스 로직 2 실행"));
    }
}
