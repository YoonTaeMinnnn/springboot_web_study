package jpabasic.ex1jellojpa.controller;

import jpabasic.ex1jellojpa.exception.CustomException;
import jpabasic.ex1jellojpa.exception.TestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.weaver.ast.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ExceptionHandler({CustomException.class, TestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult error(Exception e) {
        return new ErrorResult("custom error", e.getMessage());
    }

    @Data
    @AllArgsConstructor
    static class ErrorResult {
        private String code;

        private String message;
    }

    @GetMapping("/exp")
    public void expTest() {

        throw new CustomException("커스텀 예외");
    }

    @GetMapping("/exp2")
    public void expTestV2() {

        throw new TestException("테스트 예외");
    }
}
