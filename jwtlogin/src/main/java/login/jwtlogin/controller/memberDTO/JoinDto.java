package login.jwtlogin.controller.memberDTO;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class JoinDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String sex;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String university;

    @NotBlank
    private String dept;

    private Integer sno;   //ex) 16, 17
}
