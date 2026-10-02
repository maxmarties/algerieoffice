package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, UUID>, TestimonialRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean existsByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@Query("select count(t.id) from Testimonial t where t.id in :lines")
	long countTestimonials(@Param("lines") List<UUID> lines);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 */
	@Modifying
	@Query("delete from Testimonial t where t.id in :lines")
	void deleteTestimonials(@Param("lines") List<UUID> lines);
	
}
