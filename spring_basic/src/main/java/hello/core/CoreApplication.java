package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication  //@ComponentScan 포함 -> 컴포넌트 스캔의 탐색 시작 위치 -> hello.core -> 프로젝트 최상단 위치하는것이 관례.
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}


}
