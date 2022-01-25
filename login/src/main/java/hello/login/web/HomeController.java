package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //@GetMapping("/")
    public String homeV2(HttpServletRequest request, Model model) { //required=false (로그인x 사용자도 홈화면에 접속가능해야함)
        Member member = (Member) sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeV3(HttpServletRequest request, Model model) { //required=false (로그인x 사용자도 홈화면에 접속가능해야함)

        HttpSession session = request.getSession(false);  //세션이 없으면 생성하지 말아야 한다.

        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }

    //기본 세션 기능 이용
    @GetMapping("/")
    public String homeV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
       //required=false (로그인x 사용자도 홈화면에 접속가능해야함)

        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }

    //argumentResolver 이용
    //@GetMapping("/")
    public String homeLoginArgumentResolver(@Login Member member, Model model) {
        //required=false (로그인x 사용자도 홈화면에 접속가능해야함)

        if (member == null) {
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }
}












