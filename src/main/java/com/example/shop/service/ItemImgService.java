package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        // 파일명
        String imgName = "";

        // 원본 이미지 파일명
        String oriImgName = itemImgFile.getOriginalFilename();;

        // 경로
        String imgUrl= "";

        // 파일 업로드
//        if (!oriImgName.isEmpty()) {
        // depreceated
        if (StringUtils.hasText(oriImgName)) {
        //if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile);
            imgUrl = "/images/item/" + imgName;
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);

    }

}
