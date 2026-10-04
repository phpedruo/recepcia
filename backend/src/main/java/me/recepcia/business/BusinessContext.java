package me.recepcia.business;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * A empresa da sessão: a única empresa cujos dados o código pode ler e gravar (ADR 0004).
 *
 * <p>Ela vale só dentro do bloco passado para {@link #runAs} ou {@link #callAs}, e só na thread
 * que o executa. Fora dele não há empresa, e nenhum dado de empresa aparece. Por ser um
 * {@link ScopedValue}, e não um {@link ThreadLocal}, ela termina junto com o bloco e nunca passa
 * para a próxima requisição atendida pela mesma thread.
 */
public final class BusinessContext {

	private static final ScopedValue<UUID> CURRENT = ScopedValue.newInstance();

	private BusinessContext() {
	}

	/** A empresa da sessão, se houver. */
	public static Optional<UUID> current() {
		return CURRENT.isBound() ? Optional.of(CURRENT.get()) : Optional.empty();
	}

	/** Executa a operação tendo a empresa informada como empresa da sessão. */
	public static void runAs(UUID businessId, Runnable operation) {
		ScopedValue.where(CURRENT, Objects.requireNonNull(businessId)).run(operation);
	}

	/** Executa a operação tendo a empresa informada como empresa da sessão e devolve o resultado. */
	public static <T, X extends Throwable> T callAs(UUID businessId,
			ScopedValue.CallableOp<? extends T, X> operation) throws X {
		return ScopedValue.where(CURRENT, Objects.requireNonNull(businessId)).call(operation);
	}

}
