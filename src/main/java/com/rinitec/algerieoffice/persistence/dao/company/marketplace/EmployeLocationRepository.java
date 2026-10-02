package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeLocation;

public interface EmployeLocationRepository extends JpaRepository<EmployeLocation, UUID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeUUID
	 */
	void deleteByEmployeUUID(UUID employeUUID);

	/**
	 * VERSION BEGIN 03/2021
	 * @param employeUUID
	 * @return
	 */
	@Query("select e.location from EmployeLocation e where e.employeUUID = ?1")
	List<Integer> findLocationsByEmployeId(UUID employeUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EmployeLocation e where e.employeUUID in :lines")
	void deleteEmployeLocationByEmployeIds(@Param("lines") List<UUID> lines);
	
}
