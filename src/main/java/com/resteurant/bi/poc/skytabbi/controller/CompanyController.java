package com.resteurant.bi.poc.skytabbi.controller;


import com.resteurant.bi.poc.skytabbi.loader.ItemGenerator;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemDto;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company")
public class CompanyController {
    private final ItemService itemService;
    private final ItemGenerator itemGenerator;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Item> getAllSales() {
        return itemService.getAll();
    }


    /*@ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Item> findBy() {
        return itemService.getAll();
    }*/


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/groupByPlaces/{from}/{to}")
    public List<PlaceRevenue> revenueByPlaces(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return itemService.getRevenuePlaces(from,to);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/generate/{itemsAmount}")
    public void genereateItems(@PathVariable Integer itemsAmount) {
        LocalDateTime startDate = LocalDateTime.of(2022, 01, 30, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 10, 11, 0, 0);
        itemGenerator.generateData(10, itemsAmount, startDate, endDate);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Item createItem(@Valid @RequestBody ItemDto item) {
        return itemService.create(ItemDto.transform(item));
    }
}