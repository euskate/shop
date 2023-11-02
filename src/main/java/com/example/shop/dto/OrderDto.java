package com.example.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class OrderDto {
    
    private Long itemId;
    
    @Min(value = 1, message = "최소주문 수량은 1개")
    @Max(value = 999, message = "최대주문수량 999개")
    private int count;
}
