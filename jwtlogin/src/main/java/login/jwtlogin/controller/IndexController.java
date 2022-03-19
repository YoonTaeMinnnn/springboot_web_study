package login.jwtlogin.controller;

import login.jwtlogin.controller.memberDTO.JoinDto;
import login.jwtlogin.domain.Member;
import login.jwtlogin.error.ErrorResult;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class IndexController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public Object join(@RequestBody JoinDto joinDto) {
        if (memberRepository.findByLoginId(joinDto.getLoginId()).isPresent()) {
            return new ErrorResult("JOIN_ID_ERROR", "이미 존재하는 아이디입니다");
        }
        Member member = new Member(joinDto.getName(), joinDto.getLoginId(), bCryptPasswordEncoder.encode(joinDto.getPassword()),
                "ROLE_USER", joinDto.getEmail(), joinDto.getUniversity(), joinDto.getDept(), 0L);
        memberRepository.save(member);
        return "회원가입 완료";
    }


}
