package hello.core.singleTon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);  //싱글톤 설계

        //ThreadA : A사용자 10000원 주문
        int price1 = statefulService1.order("userA",10000);
        //ThreadB : B사용자 20000원 주문
        int price2 = statefulService2.order("userB",20000);

        //ThreadA: 사용자A가 주문 금액 조회
        //int price1 = statefulService1.getPrice();
        System.out.println("price1 = " + price1); //20000원 출력됨 => 문제
        System.out.println("price2 = " + price2);
        //Assertions.assertThat(statefulService1.getPrice()).isNotEqualTo(statefulService2.getPrice()); //다르지 않음 -> 같음 -> 문제

    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }


}
