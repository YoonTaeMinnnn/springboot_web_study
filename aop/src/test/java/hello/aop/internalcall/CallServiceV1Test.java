package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

//@Import(CallLogAspect.class)
//@Slf4j
//@SpringBootTest
//class CallServiceV1Test {
//
//    @Autowired
//    CallServiceV1 callServiceV1;
//
//    @Test
//    void external() {
//        callServiceV1.external();
//    }
//
//    @Test
//    void internal() {
//    }
//}