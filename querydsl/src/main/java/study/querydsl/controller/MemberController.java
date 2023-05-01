package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.repository.MemberJpaRepository;
import study.querydsl.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepositoy;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchDto memberSearchDto) {
        Member member = new Member("DSa",25);
        Optional<Member> findMember = memberRepositoy.findById(5L);



        List<Object> list = new ArrayList<>();
        list.add(member);
        for (Object o : list) {

        }
        log.info("sda ={}", list.get(0));
        return memberJpaRepository.search(memberSearchDto);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchDto memberSearchDto, Pageable pageable) {
        return memberRepositoy.searchPageComplex(memberSearchDto, pageable);
    }

    @GetMapping("/v2/membersTest")
    public Slice<MemberTeamDto> orderMembersList(Pageable pageable) {
        return memberRepositoy.findMemberList(pageable);
    }
}
