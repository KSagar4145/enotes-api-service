package com.enotes.app.config.audit;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditAwareConfig implements AuditorAware<Integer> {

	@Bean
	public AuditorAware<Integer> auditAware() {
		return new AuditAwareConfig();
	}

	@Override
	public Optional<Integer> getCurrentAuditor() {
		return Optional.of(1);
	}

}
