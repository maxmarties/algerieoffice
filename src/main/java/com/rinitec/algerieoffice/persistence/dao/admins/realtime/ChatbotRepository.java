package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;

public interface ChatbotRepository extends JpaRepository<Chatbot, UUID>, ChatbotRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteByCompanyId(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param now
	 */
	@Modifying
    @Query("delete from Chatbot c where c.postedDate <= ?1")
    void deleteAllExpiredSince(DateTime now);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Chatbot c where c.companyId = :companyId and c.account = null and c.id in :lines")
	long countChatbots(@Param("companyId") Long companyId, @Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(c.id) from Chatbot c where c.id in :lines")
	long countChatbots(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Chatbot c where c.id in :lines")
	void deleteChatbots(@Param("lines") List<UUID> lines);
	
}
