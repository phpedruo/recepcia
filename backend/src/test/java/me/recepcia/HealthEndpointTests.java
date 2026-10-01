package me.recepcia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class HealthEndpointTests {

	@Autowired
	private MockMvcTester mvc;

	// "UP" só aparece com o banco no ar e as migrações do Flyway aplicadas.
	@Test
	void reportsUpWhenTheDatabaseIsReachable() {
		assertThat(mvc.get().uri("/actuator/health"))
			.hasStatusOk()
			.bodyJson().extractingPath("$.status").isEqualTo("UP");
	}

}
