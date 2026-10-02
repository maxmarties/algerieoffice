package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.AvatarID;
import com.rinitec.algerieoffice.persistence.modal.medias.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, AvatarID> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param avatarID
	 * @return
	 */
	Avatar findByAvatarID(AvatarID avatarID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param avatarID
	 */
	void deleteByAvatarID(AvatarID avatarID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param avatarID
	 * @return
	 */
	@Query("select a.filename from Avatar a where a.avatarID = ?1")
	Optional<String> findFilenameByAvatarID(AvatarID avatarID);
	
}
