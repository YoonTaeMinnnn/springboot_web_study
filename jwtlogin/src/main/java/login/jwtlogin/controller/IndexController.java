package login.jwtlogin.controller;

import login.jwtlogin.auth.ConfirmationTokenService;
import login.jwtlogin.auth.PrincipalDetailService;
import login.jwtlogin.auth.PrincipalDetails;
import login.jwtlogin.controller.memberDTO.JoinDto;
import login.jwtlogin.domain.Member;
import login.jwtlogin.domain.email.ConfirmationToken;
import login.jwtlogin.error.ErrorResult;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class IndexController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailService principalDetailService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/join")
    public Object join(@Validated @RequestBody JoinDto joinDto) {
        log.info(joinDto.getUniversity().getClass().getName());
        Member member = new Member(joinDto.getNickname(), joinDto.getLoginId(), bCryptPasswordEncoder.encode(joinDto.getPassword()),
                joinDto.getSex(), joinDto.getEmail(), "ROLE_USER",  joinDto.getUniversity(), joinDto.getDept(), joinDto.getSno(), 0L);
        memberRepository.save(member);
        return "success";
    }

    @PostMapping("/duplicate-loginId")
    public Object duplicateId(@RequestBody @Validated @NotBlank String loginId){
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            return new ErrorResult("JOIN_ID_ERROR", "이미 존재하는 아이디입니다");
        } else{
            return "success";
        }
    }

    @PostMapping("/duplicate-nickname")
    public Object duplicateNickName(@RequestBody @Validated @NotBlank String nickname) {
        log.info(nickname);
        log.info(nickname.getClass().getName());
        if (memberRepository.findByNickName(nickname).isPresent()) {
            return new ErrorResult("JOIN_NICKNAME_ERROR", "이미 존재하는 닉네임입니다");
        } else{
            return "success";
        }
    }

    //메일로 url 보내기
    @PostMapping("/mail-auth")
    public void mailAuthReq(@RequestBody @Email String email) {
        confirmationTokenService.createEmailConfirmationToken(email);
    }


    //메일의 url 로 접속 시 동작
    @GetMapping("/confirm-email")
    public Object mailAuthGet(@RequestParam String token) {
        log.info(token);
        Optional<ConfirmationToken> result = principalDetailService.confirmEmail(token);
        if(result.isEmpty()){
            return new ErrorResult("EMAIL_FAIL", "이메일 인증에 실패했습니다");
        }
        return "success";
    }







}
