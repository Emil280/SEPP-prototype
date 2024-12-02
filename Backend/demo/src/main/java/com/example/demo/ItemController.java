package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@RequestMapping(path = "api/v1/item")
public class ItemController {
    public List<Item> getItems{
        return(
                List.of(new Item(1,1,"name",1,1))
                )
    }

}