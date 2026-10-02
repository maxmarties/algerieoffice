package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;

public interface PostDetailRepository extends JpaRepository<PostDetail, UUID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param urlExtern
	 * @return
	 */
	boolean existsByUrlExtern(String urlExtern);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from PostDetail p where p.id in :lines")
	void deletePostDetails(@Param("lines") List<UUID> lines);
	
}
