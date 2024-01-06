package com.resteurant.bi.poc.skytabbi.service;

import java.util.List;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;

public interface ItemService {

    List<Item> getAll();

    Item create(Item item) ;

}
