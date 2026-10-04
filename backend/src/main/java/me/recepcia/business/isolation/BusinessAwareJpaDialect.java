package me.recepcia.business.isolation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

// Informa ao PostgreSQL a empresa da sessão logo que cada transação começa, para a segurança em
// nível de linha (migração V2). O "true" do set_config limita o valor à transação, então a
// conexão volta ao pool sem empresa nenhuma. Se o aviso falhar, o JpaTransactionManager desfaz
// a transação, como em qualquer falha ao começar uma.
class BusinessAwareJpaDialect extends HibernateJpaDialect {

	@Override
	public Object beginTransaction(EntityManager entityManager, TransactionDefinition definition)
			throws PersistenceException, SQLException, TransactionException {
		Object transactionData = super.beginTransaction(entityManager, definition);
		Session session = entityManager.unwrap(Session.class);
		// A mesma empresa com que o Hibernate filtra, para as duas camadas nunca discordarem.
		if (session.getTenantIdentifierValue() instanceof UUID businessId
				&& !BusinessTenantResolver.NO_BUSINESS.equals(businessId)) {
			session.doWork((connection) -> {
				try (PreparedStatement statement = connection
					.prepareStatement("select set_config('app.business_id', ?, true)")) {
					statement.setString(1, businessId.toString());
					statement.execute();
				}
			});
		}
		return transactionData;
	}

}
