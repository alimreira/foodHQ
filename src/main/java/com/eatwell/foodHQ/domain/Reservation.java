package com.eatwell.foodHQ.domain;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name ="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @CreationTimestamp
    private LocalDateTime dateTime;
    @UpdateTimestamp
    private LocalDateTime timeUpdate;

}
