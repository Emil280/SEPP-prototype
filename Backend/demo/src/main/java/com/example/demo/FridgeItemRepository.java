package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem,Integer> {

    @Query(value = "SELECT * FROM FridgeItem WHERE user_id = :userId, item_id = :itemId", nativeQuery = true)
    Optional<FridgeItem> findFridgeItemByIds(int userId, int itemId);
}
