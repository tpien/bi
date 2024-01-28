package com.resteurant.bi.poc.skytabbi.service;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;

import java.util.List;

public interface ItemService {


    List<PlaceRevenue> getRevenuePlaces(long fromDate, long toDate);


    List<Item> getAll();

    List<Item> findByPlace(String place);

    List<Item> findByPlaceAndSaleDatePeriod(String place, long fromDate, long toDate, int page);

    Item create(Item item);

}
