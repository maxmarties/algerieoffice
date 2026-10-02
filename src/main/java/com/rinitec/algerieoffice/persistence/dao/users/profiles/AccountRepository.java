package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pseudo
	 * @return
	 */
	boolean existsByPseudo(String pseudo);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pseudo
	 * @return
	 */
	Account findByPseudo(String pseudo);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select a.pseudo from Account a where a.userId = ?1")
	Optional<String> findPseudoById(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@Query("select a.hasAccepte from Account a where a.userId = ?1")
	Optional<Boolean> findHasAccepteById(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param completed
	 * @param userId
	 */
	@Modifying
	@Query("update Account a set a.completed = ?1 where a.userId = ?2")
	void updateCompletedById(int completed, Long userId);
	
}
