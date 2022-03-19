package login.jwtlogin.controller.memberDTO;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class JoinDto {

    @NotNull
    private String name;

    private String loginId;

    private String password;

    @Email
    private String email;

    private String university;

    private String dept;
}
