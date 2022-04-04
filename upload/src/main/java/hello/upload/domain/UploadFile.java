package hello.upload.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName;  //사용자가 업로드 한 이름

    private String storeFileName;  //서버에서 저장한 이름 (중복 되지 않게 하기 위해)


}
