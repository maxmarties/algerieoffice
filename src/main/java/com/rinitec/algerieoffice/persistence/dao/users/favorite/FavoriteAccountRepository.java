package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;

public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, UUID>, FavoriteAccountRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	long countByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param accountId
	 * @return
	 */
	long countByAccountId(Long accountId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param accountId
	 * @return
	 */
	boolean existsByUserIdAndAccountId(Long userId, Long accountId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param accountId
	 * @return
	 */
	FavoriteAccount findByUserIdAndAccountId(Long userId, Long accountId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param accountId
	 */
	void deleteByAccountId(Long accountId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param lines
	 * @return
	 */
	@Query("select count(f.id) from FavoriteAccount f where f.userId = :userId and f.id in :lines")
	long countFavoriteAccounts(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from FavoriteAccount f where f.id in :lines")
	void deleteFavoriteAccounts(@Param("lines") List<UUID> lines);
	
}
