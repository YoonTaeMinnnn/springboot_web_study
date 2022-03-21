package login.jwtlogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }
}
