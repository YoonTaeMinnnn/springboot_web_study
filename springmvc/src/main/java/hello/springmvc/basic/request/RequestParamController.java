package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}",name,age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge){
        log.info("username={}, age={}",memberName, memberAge);
        return "ok";
    }


    //V3, V4 : 파라미터 이름과 동일해야 가능!
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age){
        log.info("username={}, age={}",username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,  int age){
        log.info("username={}, age={}",username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")          //username은 필수로 있어야됌
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age){  //Integer형만 null값 가능
        log.info("username={}, age={}",username, age);
        return "ok";
    }

    //기본값 설정 (빈 문자 or 파라미터x 경우)
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") Integer age){
        log.info("username={}, age={}",username, age);
        return "ok";
    }

    //맵으로 받기
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String,Object> map){
        log.info("username={}, age={}",map.get("username"), map.get("age"));
        return "ok";
    }

    //ModelAttribute ==> 요청 파라미터들을 객체로!
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){

        log.info("helloData.getUsername()={}, helloData.getAge={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData.toString()={}", helloData.toString());
        return "ok";
    }

    //생략가능
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){

        log.info("helloData.getUsername()={}, helloData.getAge={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData.toString()={}", helloData.toString());
        return "ok";
    }

}
