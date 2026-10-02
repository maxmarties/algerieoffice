package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;

public interface EmployeDetailRepository extends JpaRepository<EmployeDetail, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select e.domaine from EmployeDetail e where e.id = ?1")
	Optional<Integer> findDomaineById(UUID id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EmployeDetail e where e.id in :lines")
	void deleteEmployeDetails(@Param("lines") List<UUID> lines);
	
}
