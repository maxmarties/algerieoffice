package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.admins.data.IdentityHistory;

public interface IdentityHistoryRepository extends JpaRepository<IdentityHistory, UUID>, IdentityHistoryRepositoryCustom {
	
}
