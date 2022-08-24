package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUserName();
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("member1", 10));
    }


    // 권장하지 않음
    @GetMapping("/members2/{id}")
    public String findMemberV2(@PathVariable("id") Member member) {
        return member.getUserName();
    }
}
