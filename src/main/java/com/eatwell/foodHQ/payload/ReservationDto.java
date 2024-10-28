package com.eatwell.foodHQ.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private long id;
    private String email;
    @CreationTimestamp
    private LocalDateTime dateTime;
    @UpdateTimestamp
    private LocalDateTime timeUpdate;
}

