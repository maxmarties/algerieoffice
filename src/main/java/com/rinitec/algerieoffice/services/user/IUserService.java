package com.rinitec.algerieoffice.services.user;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;

public interface IUserService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param password
	 */
	void changePassword(User user, String password);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param password
	 * @return
	 */
	boolean checkIfValidOldPassword(User user, String password);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param usersId
	 * @return
	 */
	List<String> findAllEmail(List<Long> usersId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<Long> findAllIdByCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	String findUsermail(Long userId);
	
}
