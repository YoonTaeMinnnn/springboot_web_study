package login.jwtlogin.auth;

import login.jwtlogin.domain.email.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;

    /**
     *
     * @param memberId : 유저 아이디
     * @param receiverEmail : 받는 이메일 주소
     * @return : 생성한 토큰
     */
    public String createEmailConfirmationToken(Long memberId, String receiverEmail) {
        ConfirmationToken confirmationToken = ConfirmationToken.createEmailConfirmationToken(memberId);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject("대학교 이메일 인증");
        simpleMailMessage.setText("http://localhost:8080/confirm-email?token="+confirmationToken.getId()); //생성한 토큰 쿼리파라미터로 전송
        emailSenderService.sendEmail(simpleMailMessage);
        return confirmationToken.getId();
    }

    /**
     * 토큰 검증 메소드
     * @param tokenId : 사용자가 링크를 타서 전송된 토큰
     * @return
     */
    public ConfirmationToken findExpiredToken(String tokenId) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.find(tokenId, LocalDateTime.now(), false);
        if(confirmationToken.isEmpty()){
            log.info("없어요");
            throw new RuntimeException("이메일 인증 실패");
        }else{
            return confirmationToken.get();
        }

    }

}
