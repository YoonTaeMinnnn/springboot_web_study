package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component target;

    public TimeDecorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("time decorator 실행");
        long startTime = System.currentTimeMillis();

        String result = target.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("time decorator 종료 resultTime = {}ms", resultTime);

        return result;
    }
}
