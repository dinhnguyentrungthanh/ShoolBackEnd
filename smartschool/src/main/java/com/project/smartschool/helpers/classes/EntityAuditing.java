package com.project.smartschool.helpers.classes;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class EntityAuditing implements AuditorAware<String> {
	
    @Override
    public Optional<String> getCurrentAuditor() {
       String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(userName);
    }
}
