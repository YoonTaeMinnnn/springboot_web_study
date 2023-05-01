package study.querydsl.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class Custom implements MultipartFile {

    String name;

    String originalFilename;

    String contentType;

    private final byte[] bytes;

    public Custom(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
