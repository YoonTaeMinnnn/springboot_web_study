package hello.springmvc.basic;

import lombok.Data;

@Data  //요청 파라미터와 이름이 같아야함!
public class HelloData {
    private String username;
    private int age;


}
