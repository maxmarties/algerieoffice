package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityLike;

public interface ActualityLikeRepository extends JpaRepository<ActualityLike, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actualityId
	 * @return
	 */
	boolean existsByUserIdAndActualityId(Long userId, UUID actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param actualityId
	 */
	void deleteByActualityId(UUID actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param actualityId
	 * @return
	 */
	ActualityLike findByUserIdAndActualityId(Long userId, UUID actualityId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from ActualityLike a where a.actualityId in :lines")
	void deleteActualitiesLike(@Param("lines") List<UUID> lines);
	
}
