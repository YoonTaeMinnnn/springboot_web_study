package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public ResponseEntity<String> responseBodyV1(HttpServletResponse response) throws IOException {

        return new ResponseEntity<>("ok", HttpStatus.OK);

    }

    //@ResponseBody
    @GetMapping("/response-body-string-v2")
    public String responseBodyV2(){

        return "ok";

    }


    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setAge(12);
        helloData.setUsername("userA");
        return new ResponseEntity<HelloData>(helloData,HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.OK)  //상태 코드 지정 가능
    //@ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setAge(12);
        helloData.setUsername("userA");

        
        return helloData;

    }



}
