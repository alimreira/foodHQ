package com.eatwell.foodHQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPurchasedDto {
    private long id;
    private String nameOfItem;
    private int quantityOfItem;
    private BigDecimal totalAmount;
}
