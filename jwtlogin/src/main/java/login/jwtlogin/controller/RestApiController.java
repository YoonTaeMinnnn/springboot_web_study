package login.jwtlogin.controller;

import login.jwtlogin.auth.PrincipalDetails;
import login.jwtlogin.domain.Member;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class RestApiController {

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

    @PostMapping("/join")
    public String join(@RequestBody MemberDto memberDto) {
        Member member = new Member(memberDto.getName(), bCryptPasswordEncoder.encode(memberDto.getPassword()), "ROLE_USER");
        memberRepository.save(member);
        return "회원가입 완료";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }



    //마이페이지 => 로그인한 사용자 정보 리턴
    @GetMapping("/mypage")
    public Object myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        return new MemberInfoDto(member.getId(), member.getName(), member.getRoles());
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
