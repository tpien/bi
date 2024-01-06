package com.resteurant.bi.poc.skytabbi.es.service;

import com.resteurant.bi.poc.skytabbi.es.repository.ItemRepository;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ESItemService implements ItemService {

    private final ItemRepository itemRepository;

    private final ElasticsearchTemplate esTemplate;

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        Iterable<Item> dane = itemRepository.findAll();
        dane.forEach(items::add);
        return items;
    }


    public Item create(Item Item) {
        return itemRepository.save(Item);
    }

}
