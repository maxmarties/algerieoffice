package com.rinitec.algerieoffice.services.explorer;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.CurrentVisitor;

public interface IExplorerVisitorService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasCompanyLogin(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @param companyId
	 * @return
	 */
	CurrentVisitor readCurrentVisitor(User user, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param documentId
	 * @param type
	 * @return
	 */
	boolean hasFavoriteDocument(Long userId, String documentId, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param currentUser
	 * @param companyId
	 * @param visibility
	 * @return
	 */
	boolean hasAutorizedAnnonce(CurrentUser currentUser, Long companyId, Integer visibility);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param currentUser
	 * @param companyId
	 * @return
	 */
	ExplorerContactForm readExplorerContactForm(CurrentUser currentUser, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param currentUser
	 * @param companyId
	 * @param documentId
	 * @param type
	 * @return
	 */
	DocumentContactForm readDocumentContactForm(CurrentUser currentUser, Long companyId, String documentId, DocumentType type);
	
}
