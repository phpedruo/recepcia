package me.recepcia.business.isolation;

import java.util.Map;
import java.util.UUID;

import me.recepcia.business.BusinessContext;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

// Diz ao Hibernate qual é a empresa da sessão, para ele preencher e filtrar o business_id das
// entidades (BusinessScopedEntity).
@Component
class BusinessTenantResolver implements CurrentTenantIdentifierResolver<UUID>, HibernatePropertiesCustomizer {

	// O Hibernate exige uma empresa em toda sessão. Quando não há empresa na sessão, como no
	// login, ele recebe esta, que não existe: as consultas não trazem nada, e gravar falha.
	static final UUID NO_BUSINESS = new UUID(0, 0);

	@Override
	public UUID resolveCurrentTenantIdentifier() {
		return BusinessContext.current().orElse(NO_BUSINESS);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}

}
