package com.resteurant.bi.poc.skytabbi.model.revenue;


import lombok.Data;

@Data
public class PlaceRevenue {
    private String key;
    private double totalAmount;
    private double totalAmountGross;
}
