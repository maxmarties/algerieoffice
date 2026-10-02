package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>, CategoryRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param parentUUID
	 * @return
	 */
	boolean existsByParentUUID(UUID parentUUID);
	
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
	@Query("select count(c.id) from Category c where c.companyId = :companyId and c.id in :lines")
	long countCategories(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param name
	 * @return
	 */
	@Query("select c.id from Category c where c.companyId = ?1 and c.name = ?2")
	Optional<UUID> findIdByName(Long companyId, String name);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 */
	@Query("select c.id from Category c where c.companyId = ?1 and c.identify = ?2")
	Optional<UUID> findIdByIdentify(Long companyId, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select c.name from Category c where c.id = ?1")
	Optional<String> findNameById(UUID id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Category c where c.id in :lines")
	long countCategoriesUUID(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param parentUUID
	 */
	@Modifying
	@Query("update Category c set c.parentUUID = null where c.parentUUID = ?1")
	void trashParentUUID(UUID parentUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("update Category c set c.parentUUID = null where c.parentUUID in :lines")
	void trashParentsUUID(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Category c where c.id in :lines")
	void deleteCategories(@Param("lines") List<UUID> lines);
	
}
