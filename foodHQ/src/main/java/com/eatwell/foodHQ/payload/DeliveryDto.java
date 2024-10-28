package com.eatwell.foodHQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private long id;
    private String nameOfDeliveryMan;
    private String deliveryLocation;
}
