package com.eatwell.foodHQ.repository;

import com.eatwell.foodHQ.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
