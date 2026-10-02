package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;

public interface PostRepository extends JpaRepository<Post, UUID>, PostRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param categoryId
	 * @return
	 */
	long countByCategoryId(UUID categoryId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasTrashed
	 * @return
	 */
	long countByHasTrashed(Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param hasPublished
	 * @return
	 */
	long countByHasPublished(Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param service
	 * @return
	 */
	long countByService(Boolean service);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @return
	 */
	long countByCompanyIdAndHasTrashed(Long companyId, Boolean hasTrashed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param hasTrashed
	 * @param hasPublished
	 * @return
	 */
	long countByCompanyIdAndHasTrashedAndHasPublished(Long companyId, Boolean hasTrashed, Boolean hasPublished);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(p.id) from Post p where p.companyId = :companyId and p.id in :lines")
	long countPosts(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select p.id from Post p where p.companyId = ?1 and p.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 * @return
	 */
	@Query("select p.title from Post p where p.id = ?1")
	Optional<String> findTitleByPostId(UUID postId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 * @param companyId
	 * @return
	 */
	@Query("select p.title from Post p where p.id = ?1 and p.companyId = ?2")
	Optional<String> findTitleById(UUID postId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param categoryId
	 */
	@Modifying
	@Query("update Post p set p.categoryId = null where p.categoryId = ?1")
	void trashCategoryId(UUID categoryId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update Post p set p.categoryId = null where p.categoryId in :lines")
	void trashCategories(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Post p set p.categoryId = null where p.companyId = ?1")
	void trashAllCategories(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update Post p set p.hasTrashed = true where p.id in :lines")
	void trashPosts(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	@Modifying
	@Query("update Post p set p.hasTrashed = true where p.companyId = ?1")
	void trashAllPosts(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select p from Post p where p.companyId = ?1 and p.identify = ?2 and p.hasTrashed = false and p.hasPublished = true")
	Optional<Post> findPostByCompanyIdAndIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Post p where p.id in :lines")
	void deletePosts(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.id from Post p where p.companyId = ?1 and p.hasTrashed = true")
	List<UUID> findAllTrashedIds(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select p.id from Post p where p.companyId = ?1")
	List<UUID> findAllPostIdsByCompanyId(Long companyId);
	
}
