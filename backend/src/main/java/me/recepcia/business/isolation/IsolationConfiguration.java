package me.recepcia.business.isolation;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.transaction.autoconfigure.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration(proxyBeanMethods = false)
class IsolationConfiguration {

	// Fica no lugar do gerenciador de transações que o Spring Boot criaria, com as mesmas
	// configurações que ele receberia.
	@Bean
	JpaTransactionManager transactionManager(ObjectProvider<TransactionManagerCustomizers> customizers) {
		JpaTransactionManager transactionManager = new BusinessAwareTransactionManager();
		customizers.ifAvailable((transactionManagerCustomizers) -> transactionManagerCustomizers
			.customize(transactionManager));
		return transactionManager;
	}

}
