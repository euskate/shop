package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.ItemFormDto;
import com.example.shop.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "item")
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class Item  extends BaseEntity {

    @Id @Column(name = "item_id") @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int rest = this.stockNumber - stockNumber;
        if (rest < 0) {
            throw new OutOfStockException("상품 재고가 부족합니다. 현재재고 : " + this.stockNumber);
        }
        this.stockNumber = rest;
    }

    public void addStock(int count) {
        this.stockNumber += count;
    }


    // many to many
 /*   @ManyToMany
    @JoinTable(name = "member_item",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Member> member;*/

//    @CreationTimestamp
//    private LocalDateTime regTime;
//
//    @UpdateTimestamp
//    private LocalDateTime updateTime;


}
