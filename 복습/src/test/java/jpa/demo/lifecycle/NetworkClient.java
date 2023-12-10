package jpa.demo.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

    // 의존관계 주입 예시
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = "+url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시, 호출
    public void connect() {
        System.out.println("connect : "+url);
    }

    public void call(String message) {
        System.out.println("call = " + message);
    }

    // 서비스 종료 시, 호출
    public void disconnect() {
        System.out.println("종료");
    }


    // 의존관계 주입이 완료되고 -> 실행됨
    // 최신 스프링부트에서 적극적으로 권장하는 방식
    // 단, 외부라이브러리의 초기화&종료 메서드 호출 시, 사용하지 못함. 수동방식으로 사용해야한다.
    @PostConstruct
    public void init() {
        System.out.println("init");
        connect();
        call("초기화 연결 메시지");
    }

    // 빈 종료되기 이전에 -> 실행됨
    @PreDestroy
    public void close() {
        System.out.println("close");
        disconnect();
    }
}
