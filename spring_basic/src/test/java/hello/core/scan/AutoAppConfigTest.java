package hello.core.scan;

<<<<<<< HEAD

=======
import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
>>>>>>> bc06532442ba8b899f230a1e3cb445e77598b577
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void basicScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);


        MemberService bean = ac.getBean(MemberService.class);
        Assertions.assertThat(bean).isInstanceOf(MemberService.class);

        OrderServiceImpl bean1 = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean1.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}
