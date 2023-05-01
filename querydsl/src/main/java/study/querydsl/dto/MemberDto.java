package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;


    @QueryProjection
    public MemberDto(String username, int age) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Custom custom = new Custom(byteArrayOutputStream.toByteArray());
        this.username= username;
        this.age= age;
    }

}
