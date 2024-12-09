package com.example.demo.FridgeItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem,Integer> {

    @Query(value = "SELECT * FROM FridgeItem WHERE userid = :userId, itemid = :itemId", nativeQuery = true)
    Optional<FridgeItem> findFridgeItemByIds(int userId, int itemId);
}
