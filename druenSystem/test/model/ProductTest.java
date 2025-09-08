package model;

public class ProductTest {

	// TODO forstår ikke den her test helt. Hvad er en productqueue???

//	@Test
//	public void testProductPriorityQueue() {
//		// Create UnitConverter instance
//		UnitConverter unitConverter = new UnitConverter("kg", 1.5, 10.0);
//
//		// Create ProductCategory instance
//		ProductCategory productCategory = new ProductCategory("Electronics");
//
//		// Create Price objects
//		Price price1 = new Price(LocalDate.of(2023, 12, 1), new BigDecimal("100.0"));
//		Price price2 = new Price(LocalDate.of(2024, 1, 1), new BigDecimal("120.0"));
//		Price price3 = new Price(LocalDate.of(2024, 2, 1), new BigDecimal("110.0"));
//
//		// Create Product objects
//		Product product1 = new Product(1, "Product 1", productCategory, 50, price1, unitConverter);
//		Product product2 = new Product(2, "Product 2", productCategory, 30, price2, unitConverter);
//		Product product3 = new Product(3, "Product 3", productCategory, 40, price3, unitConverter);
//
//		// Add products to the PriorityQueue
//		PriorityQueue<Product> productQueue = new PriorityQueue<>(Product.productPriceComparator);
//		productQueue.add(product1);
//		System.out.println("Added: " + product1);
//		productQueue.add(product2);
//		System.out.println("Added: " + product2);
//		productQueue.add(product3);
//		System.out.println("Added: " + product3);
//
//		// Poll products from the PriorityQueue and print their order
//		System.out.println("\nPolling products in priority order:");
//		Product firstPolled = productQueue.poll();
//		System.out.println("Polled: " + firstPolled);
//		assertEquals(product3, firstPolled);
//
//		Product secondPolled = productQueue.poll();
//		System.out.println("Polled: " + secondPolled);
//		assertEquals(product2, secondPolled);
//
//		Product thirdPolled = productQueue.poll();
//		System.out.println("Polled: " + thirdPolled);
//		assertEquals(product1, thirdPolled);
//
//		assertTrue(productQueue.isEmpty());
//	}
//
//	

//	@Test
//	public void testProductPriorityQueueWithSamePrices() {
//		UnitConverter unitConverter = new UnitConverter("kg", 1.5,10.0);
//		ProductCategory productCategory = new ProductCategory("Electronics");
//
//		// Products with the same price but different IDs
//		Product product1 = new Product(1, "Product 1", productCategory, 50,
//				new Price(LocalDate.of(2023, 12, 1), new BigDecimal("100.0")), unitConverter);
//		Product product2 = new Product(2, "Product 2", productCategory, 30,
//				new Price(LocalDate.of(2023, 12, 1), new BigDecimal("100.0")), unitConverter);
//
//		PriorityQueue<Product> productQueue = new PriorityQueue<>(Product.productPriceComparator);
//		productQueue.add(product1);
//		productQueue.add(product2);
//
//		// Expect products to be dequeued based on insertion order when prices are equal
//		assertEquals(product1, productQueue.poll());
//		assertEquals(product2, productQueue.poll());
//	}
}