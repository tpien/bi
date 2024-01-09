package com.resteurant.bi.poc.skytabbi.service;

import java.util.List;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;

import javax.naming.directory.SearchResult;

public interface ItemService {


    public List<Item> getRevenuePlaces();


    List<Item> getAll();

    Item create(Item item) ;

}
