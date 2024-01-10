package com.resteurant.bi.poc.skytabbi.service;

import java.time.LocalDateTime;
import java.util.List;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;

public interface ItemService {


    List<PlaceRevenue> getRevenuePlaces(long from, long to);


    List<Item> getAll();

    Item create(Item item) ;

}
