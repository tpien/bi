package com.resteurant.bi.poc.skytabbi.model.revenue;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceRevenue {
    private String name;
    private double totalAmount;
    private double totalAmountGross;
    private long count;
}
