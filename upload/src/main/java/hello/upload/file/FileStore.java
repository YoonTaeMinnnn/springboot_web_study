package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    //여러개 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> store = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                store.add(uploadFile);
            }
        }
        return store;
    }

    //단일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();   //asdas.png

        String storeFile = getStoreFile(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFile)));

        return new UploadFile(originalFilename, storeFile);

    }

    private String getStoreFile(String originalFileName) {
        String ext = extractExt(originalFileName);   // png

        String uuid = UUID.randomUUID().toString();   // asdas-asfa-asf

        return uuid + "." + ext;  //ads-ads-ads.png
    }

    private String extractExt(String originalFileName) {
        int i = originalFileName.lastIndexOf(".");
        return originalFileName.substring(i + 1);
    }
}
