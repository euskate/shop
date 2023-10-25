package com.example.shop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 상품이미지 | item_img */

@Entity
@Table(name = "item_img")
@Getter @Setter
@NoArgsConstructor
public class ItemImg extends BaseEntity{

    // item_img_id
    @Id
    @Column(name = "item_img_id")
    @GeneratedValue
    private Long id;

    // 파일명
    private String imgName;

    // 원본 이미지 파일명
    private String oriImgName;

    // 경로
    private String imgUrl;

    // 대표이미지여부
    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}


