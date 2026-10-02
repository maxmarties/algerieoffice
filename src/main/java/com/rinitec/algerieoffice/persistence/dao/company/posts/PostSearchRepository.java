package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;

public interface PostSearchRepository extends JpaRepository<PostSearch, UUID>, PostSearchRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param post
	 */
	void deleteByPost(Post post);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from PostSearch p where p.id in :lines")
	void deletePostSearchs(@Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update PostSearch p set p.simultude = (p.simultude + 1) where p.id in :lines")
	void incrementSimultudes(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update PostSearch p set p.token = (p.token + 1) where p.id in :lines")
	void incrementTokens(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update PostSearch p set p.filter = (p.filter + 1) where p.id in :lines")
	void incrementFilters(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update PostSearch p set p.tag = (p.tag + 1) where p.id in :lines")
	void incrementTags(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update PostSearch p set p.view = (p.view + 1) where p.id in :lines")
	void incrementViews(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 */
	@Modifying
	@Query("update PostSearch p set p.clickCount = (p.clickCount + 1) where p.id = ?1")
	void incrementClicks(UUID postId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 */
	@Modifying
	@Query("update PostSearch p set p.workCount = (p.workCount + 1) where p.id = ?1")
	void incrementWorks(UUID postId);
	
}
