package login.jwtlogin.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import login.jwtlogin.auth.PrincipalDetails;
import login.jwtlogin.controller.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 security에서 /login post 요청으로 username, password 전송하면==>
 * ==> UsernamePasswordAuthenticationFilter가 동작함
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 실행 순서
     * 1) attemptAuthentication 정상 처리 되면
     * 2) successfulAuthentication 실행
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도중");

        /**
         * 1) username, password 받아서 정상인지 로그인 시도
         * 2) authenticationManager 로 로그인 시도를 하면 ==> PrincipalDetailsService가 호출
         * 3) PrincipalDetails 를 세션(securityContextHolder)에 담고 (권한 관리를 위해서)
         * 4) jwt 토큰을 만들어서 응답
         */

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MemberDto memberDto = objectMapper.readValue(request.getInputStream(), MemberDto.class);
            log.info("memberDto {}", memberDto.toString());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDto.getName(), memberDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("principalDetail {}", principalDetails.getUsername());
            //username이 출력됨 => 로그인 정상 처리

            //권한 관리를 spring security에 위임하기 위해 리턴
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * successfulAuthentication
     * => jwt토큰을 생성해서 response 해주는 역할
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("successful");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}