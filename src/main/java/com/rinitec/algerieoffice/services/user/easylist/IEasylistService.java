package com.rinitec.algerieoffice.services.user.easylist;

import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;

public interface IEasylistService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyForm
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistCompany addEasylistCompany(SearchCompanyForm searchCompanyForm) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param code
	 * @param wilaya
	 * @param search
	 * @param easyname
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistCompany addEasylistCompany(Long userId, String code, Integer wilaya, String search, String easyname) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistDocument addEasylistPost(SearchPostForm searchPostForm) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistDocument addEasylistAnnonce(SearchAnnonceForm searchAnnonceForm) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistDocument addEasylistEvent(SearchEventForm searchEventForm) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @return
	 * @throws MaxPlanException
	 * @throws EmptyElementException
	 * @throws MaxKeyswordException
	 */
	EasylistDocument addEasylistEmploye(SearchEmployeForm searchEmployeForm) throws MaxPlanException, EmptyElementException, MaxKeyswordException;
	
}
