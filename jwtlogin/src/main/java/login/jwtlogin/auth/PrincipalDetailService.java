package login.jwtlogin.auth;

import login.jwtlogin.domain.Member;
import login.jwtlogin.domain.email.ConfirmationToken;
import login.jwtlogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//http://localhost:8080/login 요청시 동작 (spring security 기본 로그인 주소 => /login)

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (memberRepository.findByLoginId(username).isPresent()) {
            return new PrincipalDetails(memberRepository.findByLoginId(username).get());
        } else {
            return null;
        }

    }

    @Transactional
    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findExpiredToken(token);
        Member member = memberRepository.findById(confirmationToken.getMemberId());
        log.info(member.getName());
        confirmationToken.useToken();
        member.updateGrade();
        log.info(member.getRoles());
    }


}
