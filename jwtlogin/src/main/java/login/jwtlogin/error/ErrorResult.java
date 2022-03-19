package login.jwtlogin.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResult {

    private String code;

    private String message;
}
