package me.recepcia.business.isolation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import me.recepcia.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;

// Toda tabela nova escolhe um lado: ou tem dados de empresa e segue a receita da migração V2,
// ou é global e entra em GLOBAL_TABLES (ADR 0004).
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class BusinessTablesTests {

	// Tabelas sem dados de empresa: a própria lista de empresas e o histórico do Flyway.
	private static final List<String> GLOBAL_TABLES = List.of("business", "flyway_schema_history");

	@Autowired
	private JdbcClient jdbc;

	@Test
	void everyTableWithBusinessIdHasForcedRowLevelSecurity() {
		List<String> unprotected = jdbc.sql("""
				select c.relname
				from pg_class c
				where c.relnamespace = 'public'::regnamespace
				  and c.relkind in ('r', 'p')
				  and exists (select from pg_attribute a
				              where a.attrelid = c.oid and a.attname = 'business_id' and not a.attisdropped)
				  and not (c.relrowsecurity and c.relforcerowsecurity
				           and exists (select from pg_policy p where p.polrelid = c.oid))
				order by c.relname
				""").query(String.class).list();

		assertThat(unprotected)
			.as("Tabelas com business_id sem segurança em nível de linha forçada. Siga a receita da migração V2.")
			.isEmpty();
	}

	@Test
	void everyTableWithoutBusinessIdIsDeclaredGlobal() {
		List<String> withoutBusinessId = jdbc.sql("""
				select c.relname
				from pg_class c
				where c.relnamespace = 'public'::regnamespace
				  and c.relkind in ('r', 'p')
				  and not exists (select from pg_attribute a
				                  where a.attrelid = c.oid and a.attname = 'business_id' and not a.attisdropped)
				order by c.relname
				""").query(String.class).list();

		assertThat(withoutBusinessId)
			.as("Tabelas sem business_id. Se a tabela tem dados de empresa, siga a receita da migração V2. "
					+ "Se ela é global, inclua em GLOBAL_TABLES.")
			.containsExactlyInAnyOrderElementsOf(GLOBAL_TABLES);
	}

}
