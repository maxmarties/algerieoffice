package com.rinitec.algerieoffice.persistence.dao.users;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.CompanyAvatar;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmManagerLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmModeratorLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmUserLine;
import com.rinitec.algerieoffice.web.modal.company.team.UserLine;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMessenger;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedUser;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberWidgetMini;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public interface UserRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<UserMini> findAllAdminMini();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllUserMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAdminUserMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	UserMini findAdmSupportMini();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	UserMini findSingleUserMini(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllUserCriteria(Long companyId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<UserLine> findAllUserCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllChoseUserMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	UserAccountMini findUserAccountMini(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	CompanyAvatar readUserAvatar(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActiveUser();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchMemberForm
	 * @return
	 */
	Long countAllMembersWidget(SearchMemberForm searchMemberForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchMemberForm
	 * @return
	 */
	List<MemberWidgetMini> findMembersWidgetList(SearchMemberForm searchMemberForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	ExplorerCompanyMessenger findExplorerCompanyMessenger(Long userId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmUserCriteria(Boolean filter, String search);
	
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
	List<AdmUserLine> findAllAdmUserCriteria(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmModeratorCriteria(Integer filter, String search);
	
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
	List<AdmModeratorLine> findAllAdmModeratorCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmManagerCriteria(Integer filter, String search);
	
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
	List<AdmManagerLine> findAllAdmManagerCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FollowedUser> findAllFollowedUser(Long userId, Long companyId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	Long countActiveUser(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<String> findAllNewsletter(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pro
	 * @return
	 */
	Long countUserByType(boolean pro);
	
}
