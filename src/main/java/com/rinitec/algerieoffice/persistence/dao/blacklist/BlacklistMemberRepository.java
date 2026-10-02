package com.rinitec.algerieoffice.persistence.dao.blacklist;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistMember;

public interface BlacklistMemberRepository extends JpaRepository<BlacklistMember, UUID>, BlacklistMemberRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param memberId
	 * @return
	 */
	boolean existsByUserIdAndMemberId(Long userId, Long memberId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param memberId
	 */
	void deleteByMemberId(Long memberId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param memberId
	 * @return
	 */
	BlacklistMember findByUserIdAndMemberId(Long userId, Long memberId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(b.id) from BlacklistMember b where b.userId = :userId and b.id in :lines")
	long countBlacklistMembers(@Param("userId") Long userId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from BlacklistMember b where b.id in :lines")
	void deleteBlacklists(@Param("lines") List<UUID> lines);
	
}
