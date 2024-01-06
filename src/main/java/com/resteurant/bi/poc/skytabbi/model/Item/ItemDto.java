package com.resteurant.bi.poc.skytabbi.model.Item;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemDto {

    String saleDate;
    private BigDecimal amount;
    private BigDecimal amountGross;
    private BigDecimal tax;
    private Long groupId;

    private String name;
    private String employee;
    private String place;

    public static Item transform(ItemDto itemDto) {
        Item item = new Item();
        item.setAmount(itemDto.getAmount());
        item.setEmployee(itemDto.getEmployee());
        item.setPlace(itemDto.getPlace());
        item.setTax(itemDto.getTax());
        item.setSaleDate(itemDto.getSaleDate());
        return item;
    }
}
