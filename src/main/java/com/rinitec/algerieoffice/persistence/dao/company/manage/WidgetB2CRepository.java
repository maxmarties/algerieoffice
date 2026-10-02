package com.rinitec.algerieoffice.persistence.dao.company.manage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;

public interface WidgetB2CRepository extends JpaRepository<WidgetB2C, Long> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(w.companyId) from WidgetB2C w where w.companyId in :lines")
	long countWidgets(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from WidgetB2C w where w.companyId in :lines")
	void deleteWidgets(@Param("lines") List<Long> lines);
	
}
