package hello.springmvc.basic.requestmapping;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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

    @GetMapping(value = "/mapping-consume", consumes = "application/json")  //Content-Type
    public String mappingConsumes(){
        logger.info("mappingConsumes");
        return "ok";
    }

    @GetMapping(value = "/mapping-produce", produces = "application/json")  //accept
    public Fruit mappingProduce(){
        logger.info("mappingProduce");

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        Fruit fruit = new Fruit();
        fruit.setName("apple");
        fruit.setWeight(5);
        fruit.setList(list);
        return fruit;
    }

    @Getter @Setter
    static class Fruit{
        private String name;
        private int weight;
        private List list;

    }




}
