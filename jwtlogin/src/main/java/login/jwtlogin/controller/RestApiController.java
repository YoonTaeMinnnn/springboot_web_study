package login.jwtlogin.controller;

import login.jwtlogin.domain.Member;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}