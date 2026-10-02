package com.rinitec.algerieoffice.persistence.dao.users.feedback;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;

public interface ReportRepository extends JpaRepository<Report, UUID>, ReportRepositoryCustom {

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
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(r.id) from Report r where r.id in :lines")
	long countReports(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select r.fileUUID from Report r where r.id in :lines")
	List<UUID> findAllFileUUIDById(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Report r where r.id in :lines")
	void deleteReports(@Param("lines") List<UUID> lines);
	
}
