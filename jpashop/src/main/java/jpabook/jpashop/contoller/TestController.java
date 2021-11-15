package jpabook.jpashop.contoller;


import jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @ResponseBody
    @PostMapping("/test")
    public String test(@RequestBody Test test){
        System.out.println("name = " + test.getName());
        System.out.println("age = " + test.getAge());
        return "success";
    }

    @Getter @Setter
    static class Test{
        private String name;
        private int age;
    }


}
