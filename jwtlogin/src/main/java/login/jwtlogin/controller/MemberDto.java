package login.jwtlogin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class MemberDto {

    private String name;

    private String password;

//    public MemberDto(String name) {
//        this.name = name;
//    }
}
