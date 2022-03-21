package login.jwtlogin.controller;

import login.jwtlogin.auth.PrincipalDetailService;
import login.jwtlogin.auth.PrincipalDetails;
import login.jwtlogin.controller.memberDTO.JoinDto;
import login.jwtlogin.domain.Member;
import login.jwtlogin.error.ErrorResult;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class IndexController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailService principalDetailService;

    @PostMapping("/join")
    public Object join(@Validated @RequestBody JoinDto joinDto) {
        if (memberRepository.findByLoginId(joinDto.getLoginId()).isPresent()) {
            return new ErrorResult("JOIN_ID_ERROR", "이미 존재하는 아이디입니다");
        }
        log.info(joinDto.getUniversity().getClass().getName());
        Member member = new Member(joinDto.getName(), joinDto.getLoginId(), bCryptPasswordEncoder.encode(joinDto.getPassword()),
                "ROLE_USER",  joinDto.getUniversity(), joinDto.getDept(), 0L);
        memberRepository.save(member);
        return "회원가입 완료";
    }
    //메일의 url 로 접속 시 동작
    @GetMapping("/confirm-email")
    public String mailAuthGet(@Valid @RequestParam String token) {
        log.info(token);
        principalDetailService.confirmEmail(token);
        return "이메일 인증 완료";
    }





}
