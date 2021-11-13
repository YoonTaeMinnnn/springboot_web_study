package hello.core.autowired;


import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;


public class AutoWiredTest {



    @Test
    void autoWiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        //TestBean이 스프링 빈으로 등록

    }

    static class TestBean{
        @Autowired(required = false)          //해당 스프링 빈이 없어도 동작하게 한다.
        public void setNoBean1(Member member){
            System.out.println("member1 = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member){
            System.out.println("member2 = " + member);
        }

        @Autowired
        public void setNoBean2(Optional<Member> member){
            System.out.println("member3 = " + member);
        }
    }
}
