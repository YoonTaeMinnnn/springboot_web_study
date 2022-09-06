package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchDto {

    private String username;

    private String teamName;

    private Integer ageGoe;

    private Integer ageLoe;
}
