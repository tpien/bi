package com.resteurant.bi.poc.skytabbi.controller;


import com.resteurant.bi.poc.skytabbi.loader.ItemGenerator;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.Item.ItemDto;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final ItemService itemService;
    private final ItemGenerator itemGenerator;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Item> getAllBillItems() {
        return itemService.getAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{place}")
    public List<Item> findBillItemsByPlace(
            @PathVariable String place
    ) {
        return itemService.findByPlace(place);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findByPlaceAndSalePeriod/{place}/{from}/{to}")
    public List<Item> findBillItemsByPlaceAndSaleDatePeriod(
            @PathVariable String place,
            @PathVariable long from,
            @PathVariable long to
    ) {
        return itemService.findByPlaceAndSaleDatePeriod(place, from, to);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/groupByPlaces/{from}/{to}")
    public List<PlaceRevenue> revenueByPlaces(
            @PathVariable long from,
            @PathVariable long to) {
        return itemService.getRevenuePlaces(from, to);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/generate/{itemsAmount}")
    public void genereateItems(@PathVariable Integer itemsAmount) {
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 30, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 10, 11, 0, 0);
        itemGenerator.generateData(10, itemsAmount, startDate, endDate);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Item createItem(@Valid @RequestBody ItemDto item) {
        return itemService.create(ItemDto.transform(item));
    }
}