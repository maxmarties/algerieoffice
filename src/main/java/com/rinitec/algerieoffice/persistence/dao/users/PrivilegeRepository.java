package com.rinitec.algerieoffice.persistence.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.users.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param name
	 * @return
	 */
	Privilege findByName(String name);

}
