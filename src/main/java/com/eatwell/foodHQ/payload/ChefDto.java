package com.eatwell.foodHQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChefDto {
    private long id;
    private String name;
    private String shift;
    private String level;
    private String speciality;
}
