package jpa.demo.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

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
        System.out.println("close");
    }


    // 의존관계 주입이 완료되고 -> 실행됨
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    // 빈 종료되기 이전에 -> 실행됨
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
        disconnect();
    }
}
