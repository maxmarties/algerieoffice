package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;

public interface OrderPostalRepository extends JpaRepository<OrderPostal, UUID>, OrderPostalRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param serial
	 * @return
	 */
	boolean existsBySerial(String serial);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(o.id) from OrderPostal o where o.id in :lines")
	long countPostal(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from OrderPostal o where o.id in :lines")
	void deletePostal(@Param("lines") List<UUID> lines);
	
}
