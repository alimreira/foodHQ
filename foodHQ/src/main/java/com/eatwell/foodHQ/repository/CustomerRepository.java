package com.eatwell.foodHQ.repository;

import com.eatwell.foodHQ.domain.Customer;
import com.eatwell.foodHQ.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Component
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //List<Customer> findByReservationId (long reservationId);
    Customer findByReservation(Reservation reservation);

    List<Customer> findByReservationIdIn (List<Long> reservationIds);
}
