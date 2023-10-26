package com.example.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, MultipartFile fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + ext;
        String fileUploadPath = uploadPath + "/" + savedFileName;
        fileData.transferTo(new File(fileUploadPath));
//        FileOutputStream fileOutputStream = new FileOutputStream(fileUploadPath);
//        fileOutputStream.write(fileData);
//        fileOutputStream.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws IOException {
        File file = new File(filePath);

        if(file.exists()) {
            file.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("피일이 존재하지 않습니다.");
        }
    }
}
