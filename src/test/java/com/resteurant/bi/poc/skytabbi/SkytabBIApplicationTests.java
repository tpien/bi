package com.resteurant.bi.poc.skytabbi;

import com.resteurant.bi.poc.skytabbi.loader.ItemGenerator;
import com.resteurant.bi.poc.skytabbi.model.Item.Item;
import com.resteurant.bi.poc.skytabbi.model.revenue.PlaceRevenue;
import com.resteurant.bi.poc.skytabbi.service.ItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkytabBIApplicationTests {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemGenerator itemGenerator;

    @Autowired
    private ElasticsearchTemplate template;

    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new TestsElasticsearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertTrue(elasticsearchContainer.isRunning());
        reloadIndexesLoads();
    }

    @Test
    void testPlaceRevenue() {
        itemService.create(Item.builder()
                .place("California")
                .amount(new BigDecimal(100))
                .amountGross(new BigDecimal(100))
                .tax(new BigDecimal(20))
                .employee("Robert Smith")
                .saleDate(ItemGenerator.fromStringToDate("2023-12-30T11:55:14"))
                .build());

        itemService.create(Item.builder()
                .place("San Diego")
                .amount(new BigDecimal(150))
                .amountGross(new BigDecimal(170))
                .tax(new BigDecimal(20))
                .employee("Andrew Ford")
                .saleDate(ItemGenerator.fromStringToDate("2023-11-28T16:55:14"))
                .build());

        itemService.create(Item.builder()
                .place("San Diego")
                .amount(new BigDecimal(100))
                .amountGross(new BigDecimal(120))
                .tax(new BigDecimal(20))
                .employee("Andrew Ford")
                .saleDate(ItemGenerator.fromStringToDate("2023-11-30T16:55:14"))
                .build());
        PlaceRevenue source1 = PlaceRevenue.builder().name("San Diego").totalAmount(250).totalAmountGross(290).build();
        PlaceRevenue source2 = PlaceRevenue.builder().name("California").totalAmount(100).totalAmountGross(100).build();



        List<PlaceRevenue> result = itemService.getRevenuePlaces(
                LocalDateTime.parse("2023-11-20T11:55:14").toInstant(ZoneOffset.UTC).toEpochMilli(),
                LocalDateTime.parse("2024-01-30T11:55:14").toInstant(ZoneOffset.UTC).toEpochMilli()
        );

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(source1.getName(), result.get(0).getName());
        assertEquals(source1.getTotalAmount(), result.get(0).getTotalAmount());
        assertEquals(source1.getTotalAmountGross(), result.get(0).getTotalAmountGross());

        System.out.println(result.get(1));
        assertEquals(source2.getName(), result.get(1).getName());
        assertEquals(source2.getTotalAmount(), result.get(1).getTotalAmount());
        assertEquals(source2.getTotalAmountGross(), result.get(1).getTotalAmountGross());



    }

    @Test
    void testItemsGenerator() {
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 30, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 30, 11, 0, 0);
        int itemsCount = 10;
        itemGenerator.generateData(5, itemsCount, startDate, endDate);

        List<Item> items = itemService.getAll();
        assertNotNull(items);
        assertEquals(itemsCount, items.size());

    }

    @Test
    void testCreateItem() {
        final Item item = Item.builder()
                .place("California")
                .amount(new BigDecimal(100))
                .amountGross(new BigDecimal(100))
                .tax(new BigDecimal(20))
                .employee("Robert Smith")
                .saleDate(new Date())
                .build();


        Item createdItem = itemService.create(item);
        assertNotNull(createdItem);
        assertNotNull(createdItem.getId());
        assertEquals(item.getPlace(), createdItem.getPlace());
        assertEquals(item.getAmount(), createdItem.getAmount());
        assertEquals(item.getAmountGross(), createdItem.getAmountGross());
        assertEquals(item.getTax(), createdItem.getTax());
        assertEquals(item.getEmployee(), createdItem.getEmployee());
        assertEquals(item.getSaleDate(), createdItem.getSaleDate());
    }

    @Test
    void testGetAllItems() {
        itemService.create(Item.builder()
                .place("California")
                .amount(new BigDecimal(100))
                .amountGross(new BigDecimal(100))
                .tax(new BigDecimal(20))
                .employee("Robert Smith")
                .saleDate(ItemGenerator.fromStringToDate("2023-12-30T11:55:14"))
                .build());

        itemService.create(Item.builder()
                .place("San Diego")
                .amount(new BigDecimal(150))
                .amountGross(new BigDecimal(170))
                .tax(new BigDecimal(20))
                .employee("Andrew Ford")
                .saleDate(ItemGenerator.fromStringToDate("2023-11-30T16:55:14"))
                .build());

        List<Item> items = itemService.getAll();
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    void reloadIndexesLoads() {
        if (template.indexOps(Item.class).exists()) {
            template.indexOps(Item.class).delete();
            template.indexOps(Item.class).create();
        }
    }
}