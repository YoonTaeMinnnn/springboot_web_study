package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }


    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model) { //required=false (로그인x 사용자도 홈화면에 접속가능해야함)
        if (memberId == null) {
            return "home";
        }

        Member member = memberRepository.findById(memberId);

        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }
}