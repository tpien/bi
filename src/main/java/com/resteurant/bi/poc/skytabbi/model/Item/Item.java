package com.resteurant.bi.poc.skytabbi.model.Item;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Document(indexName = "item")
public class Item {

    @Id
    private String id;

    @Field(type = FieldType.Date)
    Date saleDate;
    private BigDecimal amount;
    private BigDecimal amountGross;
    private BigDecimal tax;
    private Long groupId;

    private String name;
    private String employee;
    private String place;


}
