package me.recepcia;

import org.springframework.boot.jdbc.autoconfigure.JdbcConnectionDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	// Mesma versão principal do Heroku. A imagem Debian, e não a Alpine, ordena
	// textos com acento do mesmo jeito que o banco de produção. O superusuário
	// postgres só roda o script que cria o usuário recepcia, o mesmo do compose.yaml.
	@Bean
	PostgreSQLContainer postgresContainer() {
		return new PostgreSQLContainer(DockerImageName.parse("postgres:18"))
			.withDatabaseName("recepcia")
			.withUsername("postgres")
			.withCopyFileToContainer(MountableFile.forClasspathResource("postgres/create-app-user.sh"),
					"/docker-entrypoint-initdb.d/create-app-user.sh");
	}

	// A API e o Flyway conectam com o usuário recepcia, dono das tabelas e sem poderes
	// de superusuário, como no Heroku. Assim a segurança em nível de linha vale nos testes.
	@Bean
	JdbcConnectionDetails appUserConnectionDetails(PostgreSQLContainer postgres) {
		return new JdbcConnectionDetails() {

			@Override
			public String getUsername() {
				return "recepcia";
			}

			@Override
			public String getPassword() {
				return postgres.getPassword();
			}

			@Override
			public String getJdbcUrl() {
				return postgres.getJdbcUrl();
			}

		};
	}

}
