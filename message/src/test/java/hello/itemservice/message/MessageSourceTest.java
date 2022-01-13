package hello.itemservice.message;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessage() {
        assertThrows(NoSuchMessageException.class, ()->ms.getMessage("no_code",null,null));
    }

    @Test   //메시지 못찾으면 기본메시지 출력
    void notFoundMessageCode() {
        String message = ms.getMessage("no_code", null, "기본 메시지",null);
        Assertions.assertThat(message).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {         //{0}자리에 Spring들어감
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        Assertions.assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        Assertions.assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");  //korea 없기때문에 기본값출력
    }

    @Test
    void enLang() {  //english => en
        Assertions.assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }



}
