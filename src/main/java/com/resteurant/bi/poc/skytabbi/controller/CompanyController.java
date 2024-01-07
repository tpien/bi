package com.resteurant.bi.poc.skytabbi.controller;


import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemDto;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemMapper;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * http://localhost:8080/swagger-ui/index.html
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company")
public class CompanyController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    // powinien przyjść request z parametrami. np. byPlaces, byEmployee -> grouping
    //fromDate, toDate, groupBy("person, place, ")


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Item> getAllSales() {
        return itemService.getAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Item createItem(@Valid @RequestBody ItemDto item){
        return itemService.create(ItemDto.transform(item));
    }

}
