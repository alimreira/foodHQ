package com.eatwell.foodHQ.repository;

import com.eatwell.foodHQ.domain.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter,Long> {

}
