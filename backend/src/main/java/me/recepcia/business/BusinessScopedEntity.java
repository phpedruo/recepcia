package me.recepcia.business;

import java.util.UUID;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.TenantId;

/**
 * Base das entidades com dados de uma empresa, como serviços, clientes e agendamentos (ADR 0004).
 *
 * <p>Ao gravar, o Hibernate preenche a empresa com a {@linkplain BusinessContext empresa da sessão},
 * e toda consulta só traz dados dela. A tabela da entidade segue a receita da migração V2.
 */
@MappedSuperclass
public abstract class BusinessScopedEntity {

	@TenantId
	private UUID businessId;

	public UUID getBusinessId() {
		return businessId;
	}

}
