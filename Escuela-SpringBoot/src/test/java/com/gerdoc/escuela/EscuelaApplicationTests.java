package com.gerdoc.escuela;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Requiere MySQL con la base ESCUELA y la variable DB_PASSWORD")
@SpringBootTest
class EscuelaApplicationTests {

	@Test
	void contextLoads() {
	}

}
