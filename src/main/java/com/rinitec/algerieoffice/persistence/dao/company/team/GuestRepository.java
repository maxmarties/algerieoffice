package com.rinitec.algerieoffice.persistence.dao.company.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long>, GuestRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param userId
	 * @return
	 */
	boolean existsByCompanyIdAndUserId(Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(g.id) from Guest g where g.userId = :userId and g.id in :lines")
	long countUserGuests(@Param("userId") Long userId, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(g.id) from Guest g where g.companyId = :companyId and g.id in :lines")
	long countCompanyGuests(@Param("companyId") Long companyId, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Guest g where g.id in :lines")
	void deleteUserGuests(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Guest g where g.id in :lines")
	void deleteCompanyGuests(@Param("lines") List<Long> lines);
	
}
