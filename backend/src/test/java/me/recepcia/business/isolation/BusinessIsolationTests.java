package me.recepcia.business.isolation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;

import me.recepcia.TestcontainersConfiguration;
import me.recepcia.business.BusinessContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.support.TransactionTemplate;

// O teste obrigatório de uma empresa tentando ler os dados de outra (ADR 0012), com as duas
// camadas do ADR 0004: o filtro do Hibernate e a segurança em nível de linha do PostgreSQL.
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class BusinessIsolationTests {

	@Autowired
	private IsolationProbeRepository probes;

	@Autowired
	private JdbcClient jdbc;

	@Autowired
	private TransactionTemplate transaction;

	private UUID salon;

	private UUID barbershop;

	private UUID barbershopProbeId;

	@BeforeEach
	void createTwoBusinessesWithOneRecordEach() {
		salon = createBusiness("Salão Exemplo");
		barbershop = createBusiness("Barbearia Exemplo");
		BusinessContext.runAs(salon, () -> probes.save(new IsolationProbe("do salão")));
		barbershopProbeId = BusinessContext.callAs(barbershop,
				() -> probes.save(new IsolationProbe("da barbearia")).getId());
	}

	@Test
	void aBusinessOnlySeesItsOwnRecords() {
		List<String> labels = BusinessContext.callAs(salon, () -> labels(probes.findAll()));

		assertThat(labels).containsExactly("do salão");
	}

	// Aqui o banco deixaria passar os dados da barbearia, e o Hibernate barra sozinho.
	@Test
	void hibernateAloneBlocksAnotherBusinessRecords() {
		BusinessContext.runAs(salon, () -> transaction.executeWithoutResult((status) -> {
			tellTheDatabaseTheBusinessIs(barbershop);

			assertThat(labels(probes.findAll())).doesNotContain("da barbearia");
			assertThat(probes.findById(barbershopProbeId)).isEmpty();
		}));
	}

	// Em SQL puro o filtro do Hibernate não existe, e o banco barra sozinho.
	@Test
	void theDatabaseAloneBlocksReadingAnotherBusinessRecords() {
		List<String> labels = BusinessContext.callAs(salon, () -> transaction.execute((status) -> allLabelsInSql()));

		assertThat(labels).containsExactly("do salão");
	}

	@Test
	void theDatabaseAloneBlocksWritingRecordsForAnotherBusiness() {
		assertThatThrownBy(() -> BusinessContext.runAs(salon, () -> transaction.executeWithoutResult((status) -> jdbc
			.sql("insert into isolation_probe (id, business_id, label) values (?, ?, 'intruso')")
			.params(UUID.randomUUID(), barbershop)
			.update())))
			.rootCause()
			.hasMessageContaining("row-level security");
	}

	@Test
	void withoutABusinessNothingIsVisibleNorSaved() {
		List<String> labelsInSql = transaction.execute((status) -> allLabelsInSql());

		assertThat(probes.findAll()).isEmpty();
		assertThat(labelsInSql).isEmpty();
		assertThatThrownBy(() -> probes.save(new IsolationProbe("sem empresa")))
			.rootCause()
			.hasMessageContaining("row-level security");
	}

	// Superusuários passam por cima da segurança em nível de linha. No Heroku, a API conecta
	// como dona das tabelas, sem esses poderes, e nos testes precisa ser igual.
	@Test
	void theApplicationConnectsWithoutPowersToBypassRowLevelSecurity() {
		Boolean bypassesRowLevelSecurity = jdbc
			.sql("select rolsuper or rolbypassrls from pg_roles where rolname = current_user")
			.query(Boolean.class)
			.single();

		assertThat(bypassesRowLevelSecurity).isFalse();
	}

	private UUID createBusiness(String name) {
		UUID id = UUID.randomUUID();
		jdbc.sql("insert into business (id, name) values (?, ?)").params(id, name).update();
		return id;
	}

	private void tellTheDatabaseTheBusinessIs(UUID businessId) {
		jdbc.sql("select set_config('app.business_id', ?, true)")
			.param(businessId.toString())
			.query(String.class)
			.single();
	}

	private List<String> allLabelsInSql() {
		return jdbc.sql("select label from isolation_probe").query(String.class).list();
	}

	private static List<String> labels(List<IsolationProbe> probes) {
		return probes.stream().map(IsolationProbe::getLabel).toList();
	}

}
