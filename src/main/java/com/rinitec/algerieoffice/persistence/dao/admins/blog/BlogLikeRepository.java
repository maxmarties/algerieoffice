package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogLike;

public interface BlogLikeRepository extends JpaRepository<BlogLike, UUID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @return
	 */
	long countByBlogId(UUID blogId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 */
	void deleteByBlogId(UUID blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param blogId
	 * @return
	 */
	boolean existsByUserIdAndBlogId(Long userId, UUID blogId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from BlogLike b where b.blogId in :lines")
	void deleteBlogsLike(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param blogId
	 * @return
	 */
	BlogLike findByUserIdAndBlogId(Long userId, UUID blogId);
	
}
