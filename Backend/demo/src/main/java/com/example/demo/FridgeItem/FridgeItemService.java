package com.example.demo.FridgeItem;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FridgeItemService {
    private final FridgeItemRepository fridgeItemRepository;

    public FridgeItemService(FridgeItemRepository fridgeItemRepository) {
        this.fridgeItemRepository = fridgeItemRepository;
    }

    public List<FridgeItem> getFridgeItems(){
        return fridgeItemRepository.findAll();
    }
    public Optional<FridgeItem> getFridgeItemsByIds(int userid, int itemid){
        return fridgeItemRepository.findFridgeItemByIds(userid, itemid);

    }
}
