package com.rinitec.algerieoffice.persistence.dao.analytic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.analytic.Outlook;

public interface OutlookRepository extends JpaRepository<Outlook, Long> {
	
}
