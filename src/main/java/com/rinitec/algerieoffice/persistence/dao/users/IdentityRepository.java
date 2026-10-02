package com.rinitec.algerieoffice.persistence.dao.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.IdentityID;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;

public interface IdentityRepository extends JpaRepository<Identity, IdentityID> {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param identityID
	 * @return
	 */
	Identity findByIdentityID(IdentityID identityID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param usermail
	 * @return
	 */
	List<Identity> findByUsermail(String usermail);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	@Modifying
	@Query("delete from Identity i where i.identityID.userId = ?1")
	void deleteUser(Long userId);
	
}
