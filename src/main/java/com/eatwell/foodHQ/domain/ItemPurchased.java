package com.eatwell.foodHQ.domain;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item_purchased")
public class ItemPurchased {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nameOfItem;
    private int quantityOfItem;
    private BigDecimal totalAmount;
    private String transactionType;

}
