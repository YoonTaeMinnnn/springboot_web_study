package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration  //Component에 포함
@ComponentScan(

        //basePackages =  "hello.core.member",  //탐색 시작 위치 설정 (시간 단축 위해)
        //basePackagesClasses = AutoAppConfig.class   // 클래스가 속한 패키지가 시작 위치
        // 지정하지 않으면 -> AutoAppConfig 클래스의 패키지가 시작위치가 된다.
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)  //AppConfig 제외
public class AutoAppConfig {
    
}
