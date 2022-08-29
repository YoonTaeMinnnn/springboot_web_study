package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
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

//    @PostConstruct
//    public void init() {
//        for (int i = 0; i < 100; i++) {
//            memberRepository.save(new Member("member" + i, i));
//        }
//    }


    // 권장하지 않음
    @GetMapping("/members2/{id}")
    public String findMemberV2(@PathVariable("id") Member member) {
        return member.getUserName();
    }

    // 기본 사이즈 = 20
    // 페이지 인덱스 0 부터 시작을 디폴트로 쓰는것을 권장
    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member)); //dto반환
        // page json 에 페이지 수, 사이즈, 총 데이터수 등등 포함하여 전달
        return map;
    }

}
