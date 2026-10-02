package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogAnalytic;

public interface BlogAnalyticRepository extends JpaRepository<BlogAnalytic, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from BlogAnalytic b where b.blogId in :lines")
	void deleteBlogsAnalytic(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	@Modifying
	@Query("update BlogAnalytic b set b.follow = (b.follow + 1) where b.blogId = ?1")
	void incrementFollow(UUID blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	@Modifying
	@Query("update BlogAnalytic b set b.market = (b.market + 1) where b.blogId = ?1")
	void incrementMarket(UUID blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update BlogAnalytic b set b.simultude = (b.simultude + 1) where b.blogId in :lines")
	void incrementSimultudes(@Param("lines") List<UUID> lines);
	
}
