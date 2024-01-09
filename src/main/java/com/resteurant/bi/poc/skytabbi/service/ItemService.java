package com.resteurant.bi.poc.skytabbi.service;

import java.time.LocalDateTime;
import java.util.List;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;

public interface ItemService {


    public List<PlaceRevenue> getRevenuePlaces(LocalDateTime from, LocalDateTime to);


    List<Item> getAll();

    Item create(Item item) ;

}
