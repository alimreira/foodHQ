package com.eatwell.foodHQ.repository;

import com.eatwell.foodHQ.domain.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef,Long> {

}
