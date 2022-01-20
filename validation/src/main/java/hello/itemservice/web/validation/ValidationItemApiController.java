package hello.itemservice.web.validation;

import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
@RestController
public class ValidationItemApiController {


    @PostMapping("/add")
    public Object addItem(@Validated @RequestBody ItemSaveForm saveForm, BindingResult bindingResult) {
        log.info("api 컨트롤러 호출");
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공로직 실행");
        return saveForm;
    }

}
