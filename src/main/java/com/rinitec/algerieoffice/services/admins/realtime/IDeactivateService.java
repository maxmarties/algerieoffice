package com.rinitec.algerieoffice.services.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.company.tools.DeactivateForm;

public interface IDeactivateService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param deactivateForm
	 * @return
	 */
	List<String> deactivateCompany(DeactivateForm deactivateForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param deactivateForm
	 * @return
	 */
	User deactivateUser(DeactivateForm deactivateForm);
	
}
