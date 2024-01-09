package com.resteurant.bi.poc.skytabbi.es.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import com.resteurant.bi.poc.skytabbi.es.repository.ItemRepository;
import com.resteurant.bi.poc.skytabbi.es.repository.PlaceRevenueRepository;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ESItemService implements ItemService {

    private final ItemRepository itemRepository;
    private final PlaceRevenueRepository placeRevenueRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchRestTemplate;


    @Override
    public List<Item> getRevenuePlaces() {
        Aggregation aggregation = AggregationBuilders.terms(ta -> ta.field("place").size(10000));
        Query query = NativeQuery.builder()
                .withMaxResults(0)
                .withQuery(q -> q.matchAll(ma -> ma))
                .withAggregation("distinct_place", aggregation)
                .build();


        SearchHits<Item> searchHits = elasticsearchRestTemplate.search(query, Item.class);

        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        List<ElasticsearchAggregation> aggregationsList = aggregations.aggregations();

        Buckets<StringTermsBucket> buckets = aggregationsList.get(0).aggregation().getAggregate().sterms().buckets();

        List<String> brandList = buckets.array().stream().map(bucket -> bucket.key().stringValue()).collect(Collectors.toList());
        brandList.stream().forEach(f -> System.out.println(f));


        List<Item> items = placeRevenueRepository.executeQuery();
        return items;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Item create(Item Item) {
        return itemRepository.save(Item);
    }

}
