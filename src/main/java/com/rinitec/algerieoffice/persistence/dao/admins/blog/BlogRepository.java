package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;

public interface BlogRepository extends JpaRepository<Blog, UUID>, BlogRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param autorId
	 * @return
	 */
	long countByAutorId(Long autorId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param category
	 * @param hasPublished
	 * @return
	 */
	long countByCategoryAndHasPublished(Integer category, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	boolean existsByIdentify(String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	Blog findByIdentify(String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(b.id) from Blog b where b.id in :lines")
	long countBlogs(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Blog b where b.id in :lines")
	void deleteBlogs(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select b.id from Blog b where b.autorId in :lines")
	List<UUID> findAllBlogsByAutorIds(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	@Modifying
	@Query("update Blog b set b.viewCount = (b.viewCount + 1) where b.id = ?1")
	void incrementViews(UUID blogId);
	
}
