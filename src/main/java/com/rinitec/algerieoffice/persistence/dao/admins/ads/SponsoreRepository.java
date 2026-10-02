package com.rinitec.algerieoffice.persistence.dao.admins.ads;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;

public interface SponsoreRepository extends JpaRepository<Sponsore, UUID>, SponsoreRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select s.bannerUUID from Sponsore s where s.id in :lines")
	List<UUID> findAllBannerUUIDById(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(s) from Sponsore s where s.id in :lines")
	long countSponsores(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Sponsore s where s.id in :lines")
	void deleteSponsores(@Param("lines") List<UUID> lines);
	
}
