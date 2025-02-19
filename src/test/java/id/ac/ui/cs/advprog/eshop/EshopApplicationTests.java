package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
		// Setting up for loading context
	}

	@Test
	void testMainMethod() {
		assertDoesNotThrow(() -> EshopApplication.main(new String[]{}));
	}

}
