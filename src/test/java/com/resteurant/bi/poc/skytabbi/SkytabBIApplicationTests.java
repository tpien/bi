package com.resteurant.bi.poc.skytabbi;

import com.resteurant.bi.poc.skytabbi.model.Item.Item;
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
	void testCreateBook() {
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
	void testGetAllItems(){

		itemService.create(Item.builder()
				.place("California")
				.amount(new BigDecimal(100))
				.amountGross(new BigDecimal(100))
				.tax(new BigDecimal(20))
				.employee("Robert Smith")
				.saleDate(new Date())
				.build());

		itemService.create(Item.builder()
				.place("San Diego")
				.amount(new BigDecimal(150))
				.amountGross(new BigDecimal(170))
				.tax(new BigDecimal(20))
				.employee("Andrew Ford")
				.saleDate(new Date())
				.build());

		List<Item> books = itemService.getAll();

		assertNotNull(books);
		assertEquals(2, books.size());
	}



	@Test
	void reloadIndexesLoads() {
		if (template.indexOps(Item.class).exists()) {
			template.indexOps(Item.class).delete();
			template.indexOps(Item.class).create();
		}
	}

}
