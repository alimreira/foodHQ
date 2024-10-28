package com.eatwell.foodHQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterDto {
    private long id;
    private String name;
    private String gender;
    private String shift;
}
