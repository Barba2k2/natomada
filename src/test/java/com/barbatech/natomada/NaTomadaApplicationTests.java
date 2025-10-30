package com.barbatech.natomada;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Integration test requires PostgreSQL - enable when database is available")
class NaTomadaApplicationTests {

	@Test
	void contextLoads() {
	}

}
