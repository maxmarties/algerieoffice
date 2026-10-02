package com.rinitec.algerieoffice.persistence.dao.company.overview;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;

public interface MaincatalogRepository extends JpaRepository<Maincatalog, Long> {
	
}
