package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String userName;

    public MemberDto(Member member) {
        id = member.getId();
        userName = member.getUserName();
    }
}
