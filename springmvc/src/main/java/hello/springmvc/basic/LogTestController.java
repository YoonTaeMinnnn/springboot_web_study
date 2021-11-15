package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest(){
        String name=  "spring";

        System.out.println("name = " + name);

        // 로그 레벨 trace > debug > info > warn > error
        // 개발 서버는 보통 debug 레벨로 설정
        // default 값은 info 레벨

        log.trace("trace log={}",name);
        log.debug("debug log={}",name);
        log.info("info log={}",name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);
        log.info("info name = "+name);  //훨씬 더 많은 정보를 알 수 있음

        return "ok";
    }

}
