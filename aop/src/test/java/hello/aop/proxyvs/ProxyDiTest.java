package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDiAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ProxyDiAspect.class)
@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // jdk 로 변경
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

    // 예외 -> 구체클래스타입으로 의존관계 주입 불가능! (proxy -> interface 를 구현한 것이기 때문, 구체클래스 정보는 알지 못함)
    // cglib -> 구체클래스타입 의존관계 주입 가능!
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");

    }


}
