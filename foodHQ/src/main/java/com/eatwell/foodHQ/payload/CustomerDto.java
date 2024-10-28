package com.eatwell.foodHQ.payload;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private long id;
    private String name;
    private String emailAddress;
    private ReservationDto reservationDto;
}
