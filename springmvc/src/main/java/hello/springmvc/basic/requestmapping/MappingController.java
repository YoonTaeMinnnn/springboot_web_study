package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic(){
        logger.info("hellobasic");
        return "ok";
    }

    //쿼리 파라미터, 경로변수
    @GetMapping("/mapping/{userId}")  //url 을 템플릿화 가능
    public String mappingPath(@PathVariable("userId") String data){
        logger.info("mapping path={}", data);
        return "ok";
    }




}
