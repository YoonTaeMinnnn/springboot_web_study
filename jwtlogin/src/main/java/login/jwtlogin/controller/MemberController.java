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









}
