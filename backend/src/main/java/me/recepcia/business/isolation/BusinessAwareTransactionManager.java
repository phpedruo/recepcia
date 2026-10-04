package me.recepcia.business.isolation;

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;

// O gerenciador de transações do Spring, com o dialeto que informa a empresa da sessão ao banco.
class BusinessAwareTransactionManager extends JpaTransactionManager {

	private final JpaDialect jpaDialect = new BusinessAwareJpaDialect();

	// Sem isto, ele copiaria o dialeto padrão da fábrica de EntityManagers.
	@Override
	public JpaDialect getJpaDialect() {
		return jpaDialect;
	}

}
