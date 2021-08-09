package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("Hello")         //url주소
    public String hello(Model model){
        model.addAttribute("data","Hello!!");
        return "Hello";  //Hello.html로 전달된다.
    }



    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")                     //api방식
    @ResponseBody   //http의 body부에 넣어주겠다!
    public String helloString(@RequestParam("name") String name){
        return "hello "+ name;  //문자열 그대로 출력된다. (html로 변환하지 않고!)
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);

        return hello;  //Hello클래스의 getName()이 출력된다!(json형식으로)   ex){"name":"spring!!"}  객체 -> json 반환
    }
    static class Hello{
        private String name;


        public String getName() {
            return name;
        }




        public void setName(String name) {
            this.name = name;
        }
    }
}
