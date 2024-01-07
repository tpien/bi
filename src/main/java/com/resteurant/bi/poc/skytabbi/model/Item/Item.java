package com.resteurant.bi.poc.skytabbi.model.Item;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document(indexName = "item")
public class Item {

    @Id
    private String id;


    String saleDate;
    private BigDecimal amount;
    private BigDecimal amountGross;
    private BigDecimal tax;
    private Long groupId;

    private String name;
    private String employee;
    private String place;


}
