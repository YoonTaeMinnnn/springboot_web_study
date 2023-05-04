package hello.advanced.trace.logTrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class ThreadLocalLogTraceTest {

    @Test
    void begin_end_leven2() {
        ThreadLocalLogTrace trace = new ThreadLocalLogTrace();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_end_level2_ex() {
        ThreadLocalLogTrace trace = new ThreadLocalLogTrace();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}