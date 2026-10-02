package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;

public interface PostPhotoRepository extends JpaRepository<PostPhoto, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param postUUID
	 * @return
	 */
	List<PostPhoto> findByPostUUID(UUID postUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postUUID
	 */
	void deleteByPostUUID(UUID postUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from PostPhoto p where p.postUUID in :lines")
	void deletePostPhotoByPosts(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from PostPhoto p where p.photoUUID in :lines")
	void deleteLinesPostPhoto(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postUUID
	 */
	@Modifying
	@Query("update PostPhoto p set p.hasPrincipal = false where p.postUUID = ?1")
	void clearHasPrincipalByPostId(UUID postUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photoUUID
	 */
	@Modifying
	@Query("update PostPhoto p set p.hasPrincipal = true where p.photoUUID = ?1")
	void updateHasPrincipalByPhotoId(UUID photoUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postId
	 * @return
	 */
	@Query("select p.photoUUID from PostPhoto p where p.postUUID = ?1")
	List<UUID> findAllPhotoUUIDByPostId(UUID postId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select p.photoUUID from PostPhoto p where p.postUUID in :lines")
	List<UUID> findAllPhotoUUIDByPostIds(@Param("lines") List<UUID> lines);
		
}
