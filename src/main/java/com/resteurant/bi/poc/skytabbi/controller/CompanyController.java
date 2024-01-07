package com.resteurant.bi.poc.skytabbi.controller;


import com.resteurant.bi.poc.skytabbi.loader.ItemGenerator;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemDto;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemMapper;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * http://localhost:8080/swagger-ui/index.html
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company")
public class CompanyController {
    private final ItemService itemService;
    private final ItemGenerator itemGenerator;
    private final ItemMapper itemMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Item> getAllSales() {
        return itemService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/generate")
    public List<Item> genereateItems() {
        LocalDateTime startDate = LocalDateTime.of(2023, 11, 30, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 10, 11, 0, 0);
        itemGenerator.generateData(5,10, startDate,endDate);
        return itemService.getAll();

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Item createItem(@Valid @RequestBody ItemDto item){
        return itemService.create(ItemDto.transform(item));
    }

}
