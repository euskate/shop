package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "item")
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class Item {

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

    // many to many
 /*   @ManyToMany
    @JoinTable(name = "member_item",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Member> member;*/

    @CreationTimestamp
    private LocalDateTime regTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
