package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller  //컴포넌트 스캐닝
public class MemberController {   //스프링 실행될 때, membercontroller객체(스프링 빈)를 생성하면서 생성자 호출 -> 의존성 주입(autowired)
    private final MemberService memberService;

    @Autowired  //의존성 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();  //멤버 도메인객체 생성
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";  //홈화면으로 리다이렉트
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> list = memberService.findMembers();
        model.addAttribute("members",list);
        return "/members/memberList";
    }

}
