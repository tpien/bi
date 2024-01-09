package com.resteurant.bi.poc.skytabbi.es.repository;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRevenueRepository extends ElasticsearchRepository<Item, String> {

    @Query(value = """ 
                {
                      "bool": {
                          "must":
                              {
                                  "match" : {
                                      "place" : "Hirtheland"
                                  }
                              }
                      }
                  }
            """)
    List<Item> executeQuery();
}
