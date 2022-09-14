package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberJpaRepository;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepositoy;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchDto memberSearchDto) {
        return memberJpaRepository.search(memberSearchDto);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchDto memberSearchDto, Pageable pageable) {
        return memberRepositoy.searchPageComplex(memberSearchDto, pageable);
    }
}
