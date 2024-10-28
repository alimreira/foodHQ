package com.eatwell.foodHQ.payload;

import javax.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;
    private String address;
    private String paymentMethod;
    private String status;
}
