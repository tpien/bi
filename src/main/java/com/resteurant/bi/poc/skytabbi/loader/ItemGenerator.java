package com.resteurant.bi.poc.skytabbi.loader;

import com.github.javafaker.Faker;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ItemGenerator {

    @Autowired
    private ItemService itemService;

    public void generateData(int maxPlaces, Integer itemsAmount, LocalDateTime startDate, LocalDateTime endDate) {

        BigDecimal[] taxRates = {
                BigDecimal.valueOf(1.05), // 5%
                BigDecimal.valueOf(1.08), // 8%
                BigDecimal.valueOf(1.23)  // 23%
        };

        List<String> places = new ArrayList<>();
        for (int i = 0; i < maxPlaces; i++)
            places.add(Faker.instance().address().cityName());

        List<String> dishes = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            dishes.add(Faker.instance().food().dish());


        for (int i = 0; i < itemsAmount; i++) {
            BigDecimal randomTaxRate = taxRates[ThreadLocalRandom.current().nextInt(taxRates.length)];
            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 101));
            BigDecimal amountGross = amount.multiply(randomTaxRate);

            long seconds = startDate.until(endDate, ChronoUnit.SECONDS);
            long randomSeconds = ThreadLocalRandom.current().nextLong(seconds + 1);
            LocalDateTime randomEventDate = startDate.plusSeconds(randomSeconds);

            itemService.create(Item.builder()
                    .place(places.get(new Random().nextInt(places.size())))
                    .name(dishes.get(new Random().nextInt(dishes.size())))
                    .amount(amount)
                    .amountGross(amountGross)
                    .tax(randomTaxRate)
                    .employee(Faker.instance().name().name())
                    .saleDate(fromLocalToDate(randomEventDate))
                    .build());
        }
    }



    public static Date fromStringToDate(String inDate) {
        LocalDateTime dateDateTime = LocalDateTime.parse(inDate);
        return fromLocalToDate(dateDateTime);
    }
    public static Date fromLocalToDate(LocalDateTime dateDateTime) {
        return Date.from(dateDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}