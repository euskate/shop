package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String ItemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query(value = "SELECT i " +
            "FROM Item i " +
            "WHERE i.itemDetail " +
            "LIKE %:itemDetail% " +
            "ORDER BY i.price DESC")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "SELECT * FROM ITEM WHERE ITEM.ITEM_DETAIL LIKE %:itemDetail% ORDER BY ITEM.PRICE DESC", nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

}
