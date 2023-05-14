package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // jdk 동적 프록시

        // 인터페이스로 캐스팅 -> 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        // 구체클래스로 캐스팅 -> 실패 (인터페이스 기반이기 때문)
//        MemberServiceImpl castingMemberService = (MemberServiceImpl) proxy;

        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) proxy;
        });

    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);

        // 부모클래스로 캐스팅 -> 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        // 구체클래스로 캐스팅 -> 성공 (구체클래스 기반이기 때문)
        MemberServiceImpl castingMemberService = (MemberServiceImpl) proxy;
    }

}
