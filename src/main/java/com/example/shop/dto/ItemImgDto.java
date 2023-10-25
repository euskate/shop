package com.example.shop.dto;


import com.example.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {
    private Long id;
    // 파일명
    private String imgName;
    // 원본 이미지 파일명
    private String oriImgName;
    // 경로
    private String imgUrl;
    // 대표이미지여부
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
