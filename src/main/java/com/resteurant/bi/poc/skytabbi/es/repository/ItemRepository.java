package com.resteurant.bi.poc.skytabbi.es.repository;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, String> {

}
