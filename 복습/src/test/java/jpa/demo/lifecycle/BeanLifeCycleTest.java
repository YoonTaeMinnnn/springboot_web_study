package jpa.demo.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);



        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{
        // 메서드 이름을 자유롭게 작성 가 + 외부 라이브러리의 초기화&종료 메서드 또한 호출 가능
        // 외부라이브러리의 초기화&종료 메서드는 대부분 추론 가능 (이름 : close or shutdown) -> 설정해주지 않아도 된다.
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://spring-dev.com");
            return networkClient;
        }
    }
}
