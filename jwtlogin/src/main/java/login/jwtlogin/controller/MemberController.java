package login.jwtlogin.controller;

import login.jwtlogin.controller.memberDTO.JoinDto;
import login.jwtlogin.domain.Member;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user")
public class MemberController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/token")
    public String token() {
        return "token";
    }

    @GetMapping
    public String user() {
        return "user";
    }



//    //마이페이지 => 로그인한 사용자 정보 리턴
//    @GetMapping("/mypage")
//    public Object myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        Member member = principalDetails.getMember();
//        return new MemberInfoDto(member.getId(), member.getName(), member.getRoles());
//    }



}
