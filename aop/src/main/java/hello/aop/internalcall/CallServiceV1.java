package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
//public class CallServiceV1 {
//
//    private CallServiceV1 callServiceV1;
//
//    // setter 주입 : 생성자 주입은 순환참조 발생!
//    @Autowired
//    public void setCallServiceV1(CallServiceV1 callServiceV1) {
//        this.callServiceV1 = callServiceV1;
//    }
//
//    public void external() {
//        log.info("external call");
//        callServiceV1.internal();
//    }
//
//    public void internal() {
//        log.info("internal call");
//    }
//}
