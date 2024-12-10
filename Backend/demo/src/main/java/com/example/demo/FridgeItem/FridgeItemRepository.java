package com.example.demo.FridgeItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem,Integer> {

    @Query(value = "SELECT * FROM fridge WHERE userid = :userId AND itemid = :itemId LIMIT 1", nativeQuery = true)
    Optional<FridgeItem> findFridgeItemByIds(int userId, int itemId);

    @Query(value = "SELECT * FROM fridge WHERE userid = :userId",nativeQuery = true)
    List<FridgeItem> findFridgeItemByUserId(int userId);
}
