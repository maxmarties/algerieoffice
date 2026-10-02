package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;

public interface RateRepository extends JpaRepository<Rate, UUID>, RateRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param memberId
	 */
	void deleteByMemberId(Long memberId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(r.id) from Rate r where r.id in :lines")
	long countRates(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Rate r where r.id in :lines")
	void deleteRates(@Param("lines") List<UUID> lines);
	
}
