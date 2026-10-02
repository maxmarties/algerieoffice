package com.rinitec.algerieoffice.persistence.dao.company.newsletter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long>, BudgetRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(b.id) from Budget b where b.companyId in :lines")
	long countBudget(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Budget b where b.companyId in :lines")
	void deleteBudgets(@Param("lines") List<Long> lines);
	
}
