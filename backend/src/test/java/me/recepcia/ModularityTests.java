package me.recepcia;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

	private final ApplicationModules modules = ApplicationModules.of(RecepciaApplication.class);

	// Falha se um módulo usar o interior de outro, e não só a interface pública dele (ADR 0002).
	@Test
	void modulesOnlyUseEachOthersPublicApi() {
		modules.verify();
	}

}
