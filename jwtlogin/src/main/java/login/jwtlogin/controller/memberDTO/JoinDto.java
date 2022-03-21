package login.jwtlogin.controller.memberDTO;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class JoinDto {

    @NotBlank
    private String name;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String university;

    @NotBlank
    private String dept;
}
