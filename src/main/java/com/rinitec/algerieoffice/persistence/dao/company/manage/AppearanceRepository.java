package com.rinitec.algerieoffice.persistence.dao.company.manage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;

public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
	
}
