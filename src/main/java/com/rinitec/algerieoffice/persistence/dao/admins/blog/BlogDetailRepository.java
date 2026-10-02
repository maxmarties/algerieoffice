package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogDetail;

public interface BlogDetailRepository extends JpaRepository<BlogDetail, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select b.photoUUID from BlogDetail b where b.id = ?1")
	Optional<UUID> findPhotoUUIDById(UUID id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select b.photoUUID from BlogDetail b where b.id in :lines")
	List<UUID> findAllPhotoUUIDByIds(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from BlogDetail b where b.id in :lines")
	void deleteBlogsDetail(@Param("lines") List<UUID> lines);
	
}
