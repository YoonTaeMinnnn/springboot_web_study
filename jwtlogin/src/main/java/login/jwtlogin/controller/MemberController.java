package login.jwtlogin.controller;

import login.jwtlogin.auth.ConfirmationTokenService;
import login.jwtlogin.auth.EmailSenderService;
import login.jwtlogin.auth.PrincipalDetailService;
import login.jwtlogin.auth.PrincipalDetails;
import login.jwtlogin.controller.memberDTO.JoinDto;
import login.jwtlogin.domain.Member;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user")
public class MemberController {

    private final MemberRepository memberRepository;
    private final PrincipalDetailService principalDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

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

    //메일로 url 보내기
    @PostMapping("/mail-auth")
    public void mailAuthReq(@RequestBody @Email String email, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        confirmationTokenService.createEmailConfirmationToken(member.getId(), email);
    }




//    //마이페이지 => 로그인한 사용자 정보 리턴
//    @GetMapping("/mypage")
//    public Object myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        Member member = principalDetails.getMember();
//        return new MemberInfoDto(member.getId(), member.getName(), member.getRoles());
//    }


}
