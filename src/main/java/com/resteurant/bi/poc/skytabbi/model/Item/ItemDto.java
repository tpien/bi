package com.resteurant.bi.poc.skytabbi.model.Item;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemDto {

    Date saleDate;
    private BigDecimal amount;
    private BigDecimal amountGross;
    private BigDecimal tax;
    private Long groupId;

    private String name;
    private String employee;
    private String place;

    public static Item transform(ItemDto itemDto) {
        Item item = Item.builder()
                .amount(itemDto.getAmount())
                .employee(itemDto.getEmployee())
                .place(itemDto.getPlace())
                .tax(itemDto.getTax())
                .saleDate(itemDto.getSaleDate())
                .build();
        return item;
    }
}
