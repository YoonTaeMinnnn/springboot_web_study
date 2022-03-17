package login.jwtlogin.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MemberDto {

    private String name;

    private String password;
}
