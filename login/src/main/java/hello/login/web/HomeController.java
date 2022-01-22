package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }


    //@GetMapping("/")
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

    @GetMapping("/")
    public String homeV2(HttpServletRequest request, Model model) { //required=false (로그인x 사용자도 홈화면에 접속가능해야함)
        Member member = (Member) sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }
}