package com.rinitec.algerieoffice.persistence.dao.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param enabled
	 * @return
	 */
	long countByEnabled(boolean enabled);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param locked
	 * @return
	 */
	long countByLocked(boolean locked);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param expired
	 * @return
	 */
	long countByExpired(boolean expired);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	long countByCompanyId(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	User findByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	boolean existsByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	boolean existsByIdAndCompanyId(Long id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<User> findByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	@Query("select u.id from User u where u.email = ?1")
	Optional<Long> findIdByEmail(String email);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select concat (u.firstName, ' ', u.lastName) from User u where u.id = ?1")
	Optional<String> findUsernameById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select u.email from User u where u.id = ?1")
	Optional<String> findEmailById(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select u.id from User u where u.companyId = ?1")
	List<Long> findAllIdByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	@Query("select u.email from User u where u.companyId = ?1")
	List<String> findAllEmailByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select u.email from User u where u.id in :lines")
	List<String> findAllEmailByUsersId(@Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@Query("select u.email from User u where u.admin = true")
	List<String> findAllEmailAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@Query("select u.email from User u where u.id != ?1 and u.admin = true")
	List<String> findAllEmailAdminOne(Long id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param locked
	 * @param lines
	 */
	@Modifying
	@Query("update User u set u.locked = :locked where u.id in :lines")
	void updateLockedByIds(@Param("locked") boolean locked, @Param("lines") List<Long> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select u from User u where u.admin = false and u.id in :lines")
	List<User> findAllUsers(@Param("lines") List<Long> lines);
	
}
