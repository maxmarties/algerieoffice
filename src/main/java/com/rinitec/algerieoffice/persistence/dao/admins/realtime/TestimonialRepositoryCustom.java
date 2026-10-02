package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmTestimonialLine;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberTestimonial;

public interface TestimonialRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllTestimonialCriteria(Boolean filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmTestimonialLine> findAllTestimonialCriteria(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<MemberTestimonial> findLastMemberTestimonial(int limit);
	
}
