package com.rinitec.algerieoffice.services.publics;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberProfile;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberTestimonial;
import com.rinitec.algerieoffice.web.modal.publics.members.MembersWidgetList;
import com.rinitec.algerieoffice.web.modal.user.account.PrivateProfile;

public interface IMemberService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	long countAllActiveMember();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	String findPsuedoByUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	PrivateProfile readPrivateProfile(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pseudo
	 * @return
	 */
	MemberProfile readMemberProfile(String pseudo);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchMemberForm
	 * @return
	 */
	MembersWidgetList findMembersWidgetList(SearchMemberForm searchMemberForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<MemberTestimonial> findLastMemberTestimonial(int limit);
	
}
