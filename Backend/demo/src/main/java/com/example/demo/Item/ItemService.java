package com.example.demo.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository ItemRepository;


    @Autowired
    public ItemService(ItemRepository ItemRepository) {
        this.ItemRepository = ItemRepository;
    }

    public List<Item> getItems(){
        return ItemRepository.findAll();
    }
    public Optional<Item> getItemById(Integer itemid){
        System.out.println("attempting sql query, itemid is "+" "+itemid);
        Optional<Item> item = ItemRepository.findById(itemid);
        System.out.println("item from sql query successful");
        return item;

    }
}
