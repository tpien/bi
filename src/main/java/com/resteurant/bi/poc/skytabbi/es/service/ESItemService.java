package com.resteurant.bi.poc.skytabbi.es.service;

import com.resteurant.bi.poc.skytabbi.es.repository.ItemRepository;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.Parameters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ESItemService implements ItemService {

    Logger logger = LoggerFactory.getLogger(ESItemService.class);

    private final ItemRepository itemRepository;
    private final JestClient jestClient;

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    @Override
    public List<Item> findByPlace(String place) {
        List<Item> items = new ArrayList<>();
        itemRepository.findByPlace(place).forEach(items::add);
        return items;
    }

    @Override
    public List<Item> findByPlaceAndSaleDatePeriod(String place, long fromDate, long toDate, int page) {
        List<Item> items = new ArrayList<>();
        int itemsPerPage = 10;

        PageRequest pageRequest = PageRequest.of(page, itemsPerPage);
        itemRepository.findByPlaceAndSaleDatePeriod(place, fromDate, toDate, pageRequest).forEach(items::add);
        return items;
    }

    public Item create(Item Item) {
        return itemRepository.save(Item);
    }


    final static String QUERY_GET_PLACES_REVENUE = """
            {
                 "size": 0,
                 "query": {
                     "bool": {
                         "filter": {
                             "range": {
                                 "saleDate": {
                                     "gte": ":fromSaleDate",
                                     "lte": ":toSaleDate"
                                 }
                             }
                         }
                     }
                 },
                 "aggs": {
                     "places": {
                         "terms": {
                             "field": "place.keyword"
                         },
                         "aggs": {
                             "total_amount": {
                                 "sum": {
                                     "field": "amount"
                                 }
                             },
                             "total_amountGross": {
                                 "sum": {
                                     "field": "amountGross"
                                 }
                             }
                         }
                     }
                 }
             }
            """;

    public List<PlaceRevenue> getRevenuePlaces(long fromDate, long toDate) {
        List<PlaceRevenue> placeRevenuesResult = new ArrayList<>();
        String fromEpoch = String.valueOf(fromDate);
        String toEpoch = String.valueOf(toDate);

        String query = QUERY_GET_PLACES_REVENUE.replace(":fromSaleDate", fromEpoch)
                .replace(":toSaleDate", toEpoch);

        Search search = new Search.Builder(query)
                .addIndex("item")
                .setParameter(Parameters.SIZE, 0)
                .build();
        try {
            SearchResult result = jestClient.execute(search);
            if (result.isSucceeded()) {
                io.searchbox.core.search.aggregation.TermsAggregation placesAgg = result.getAggregations().getTermsAggregation("places");
                for (io.searchbox.core.search.aggregation.TermsAggregation.Entry entry : placesAgg.getBuckets()) {
                    String place = entry.getKey();
                    Double totalAmount = entry.getSumAggregation("total_amount").getSum();
                    Double totalAmountGross = entry.getSumAggregation("total_amountGross").getSum();
                    placeRevenuesResult.add(PlaceRevenue.builder()
                            .name(place)
                            .totalAmount(totalAmount)
                            .totalAmountGross(totalAmountGross)
                            .count(entry.getCount())
                            .build()
                    );
                }
            } else {
                logger.error("Elasticsearch request failed: " + result.getErrorMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return placeRevenuesResult;
    }
}
