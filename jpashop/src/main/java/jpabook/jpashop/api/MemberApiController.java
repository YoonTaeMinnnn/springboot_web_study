package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest createMemberRequest) {
        Member member = new Member();
        member.setName(createMemberRequest.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateMemberRequest updateMemberRequest) {
        memberService.updateMember(id, updateMemberRequest.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    /**
     * list 반환시, 한번 감싸서 반환하는 것을 추천 (유연성 확장)
     * @return
     */
    @GetMapping("/api/members")
    public Result members() {
        List<Member> members = memberService.find();
        List<MemberDto> memberDtos = members.stream().map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());
        return new Result(memberDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


    @Data
    static class UpdateMemberRequest {
        @NotBlank
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }




    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }

    @Data
    static class CreateMemberRequest {
        @NotBlank
        private String name;
    }
}
