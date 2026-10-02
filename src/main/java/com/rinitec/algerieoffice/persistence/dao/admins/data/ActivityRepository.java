package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @return
	 */
	boolean existsByCode(String code);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	boolean existsByUrl(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @return
	 */
	Activity findByCode(String code);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	Activity findByUrl(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param codes
	 * @return
	 */
	@Query("select count(a.id) from Activity a where a.code in :codes")
	long countAllByCodes(@Param("codes") List<String> codes);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param codes
	 * @return
	 */
	@Query("select a from Activity a where a.code in :codes")
	List<Activity> findAllByCodes(@Param("codes") List<String> codes);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(a.id) from Activity a where a.id in :lines")
	long countActivities(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Activity a where a.id in :lines")
	void deleteActivities(@Param("lines") List<Long> lines);
	
}
