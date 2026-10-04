package me.recepcia.business.isolation;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import me.recepcia.business.BusinessScopedEntity;
import org.hibernate.annotations.UuidGenerator;

// Entidade só dos testes, na tabela isolation_probe, para provar o isolamento por empresa.
@Entity
class IsolationProbe extends BusinessScopedEntity {

	@Id
	@GeneratedValue
	@UuidGenerator(style = UuidGenerator.Style.VERSION_7)
	private UUID id;

	private String label;

	protected IsolationProbe() {
	}

	IsolationProbe(String label) {
		this.label = label;
	}

	UUID getId() {
		return id;
	}

	String getLabel() {
		return label;
	}

}
