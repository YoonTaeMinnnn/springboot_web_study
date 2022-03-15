package login.jwtlogin.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilterV3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("filter3");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setCharacterEncoding("utf-8");

        if (req.getMethod().equals("POST")) {
            String authorization = req.getHeader("Authorization");
            log.info(authorization);

            if (authorization.equals("cors")) {
                chain.doFilter(req, res);
            }else{
                PrintWriter out = res.getWriter();
                out.println(("인증안됨"));
            }
        }

        return;
    }

}
