package com.rinitec.algerieoffice.services.user.feedback;

import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.form.feedback.AppointmentForm;
import com.rinitec.algerieoffice.web.form.feedback.NoticeForm;
import com.rinitec.algerieoffice.web.form.feedback.RateForm;
import com.rinitec.algerieoffice.web.form.feedback.ReportForm;

public interface IFeedbackService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param accountId
	 * @return
	 */
	String postOrUpdateFavoriteAccount(Long userId, Long accountId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param type
	 * @return
	 */
	String postOrUpdateFavoriteCompany(Long userId, Long companyId, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param documentId
	 * @param type
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	String postOrUpdateFavoriteDocument(Long userId, String documentId, String type) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param liked
	 * @param note
	 * @return
	 */
	String postOrUpdateEvaluation(Long userId, Long companyId, boolean liked, Integer note);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param noticeForm
	 * @return
	 */
	String addNotice(Long userId, NoticeForm noticeForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param appointmentForm
	 * @return
	 */
	String addAppointment(Long userId, AppointmentForm appointmentForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param reportForm
	 * @return
	 */
	Report addReport(Long userId, ReportForm reportForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param rateForm
	 * @return
	 */
	Rate addRate(Long userId, RateForm rateForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param companyId
	 * @param function
	 * @return
	 * @throws AccessLeaderException
	 */
	String addCollaborator(Long userId, Long companyId, String function) throws AccessLeaderException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerContactForm
	 * @return
	 */
	Contact postContact(ExplorerContactForm explorerContactForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param documentContactForm
	 * @return
	 */
	GuestDocument postGuestDocument(DocumentContactForm documentContactForm);
	
}
