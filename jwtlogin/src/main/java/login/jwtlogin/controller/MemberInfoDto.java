package login.jwtlogin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoDto {

    private Long id;

    private String name;

    private String roles;
}
