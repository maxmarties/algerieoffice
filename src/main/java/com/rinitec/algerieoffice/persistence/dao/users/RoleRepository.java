package com.rinitec.algerieoffice.persistence.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * VERSION BEGIN 03/2021
	 * @param name
	 * @return
	 */
	Role findByName(String name);

}
