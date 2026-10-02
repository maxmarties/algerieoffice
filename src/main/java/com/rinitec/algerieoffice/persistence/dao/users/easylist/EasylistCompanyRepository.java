package com.rinitec.algerieoffice.persistence.dao.users.easylist;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;

public interface EasylistCompanyRepository extends JpaRepository<EasylistCompany, UUID>, EasylistCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from EasylistCompany e where e.userId = :userId and e.id in :lines")
	long countEasylistCompanies(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(e.id) from EasylistCompany e where e.id in :lines")
	long countAllEasylistCompanies(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from EasylistCompany e where e.id in :lines")
	void deleteEasylistCompanies(@Param("lines") List<UUID> lines);
	
}
