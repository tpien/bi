package com.resteurant.bi.poc.skytabbi.es.repository;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, String> {
    List<Item> findByPlace(String name);

    @Query("""
             {
                "bool": {
                  "must": [
                    {
                      "match": {
                        "place": "?0"
                      }
                    },
                    {
                      "range": {
                        "saleDate": {
                          "gte": "?1",
                          "lte": "?2"
                        }
                      }
                    }
                  ]
                }
              }
            """)
    List<Item> findByPlaceAndSaleDatePeriod(String place, long fromDate, long toDate, Pageable pageable);

}
