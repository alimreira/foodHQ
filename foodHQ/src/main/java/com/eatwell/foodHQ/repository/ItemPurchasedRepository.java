package com.eatwell.foodHQ.repository;

import com.eatwell.foodHQ.domain.ItemPurchased;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPurchasedRepository extends JpaRepository<ItemPurchased,Long> {
}
