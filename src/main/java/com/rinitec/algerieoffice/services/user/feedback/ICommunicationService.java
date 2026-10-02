package com.rinitec.algerieoffice.services.user.feedback;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.company.communication.AppointForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ContactDetail;

public interface ICommunicationService {

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
	ElementsList findNoticeList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findUserNoticeList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Notice validateNotice(String id, Long companyId, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Notice deleteNotice(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteNotices(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllNotices(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserNotice(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserNotices(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllUserNotices(Long userId);
	
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
	ElementsList findAppointList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findUserAppointList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 */
	AppointDetail readAppointDetail(String id, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param appointForm
	 * @param companyId
	 * @param userId
	 * @return
	 */
	Appointment updateAppointment(AppointForm appointForm, Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAppointment(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteAppointments(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllAppointments(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserAppointment(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserAppointments(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllUserAppointments(Long userId);
	
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
	ElementsList findEvaluationList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findUserEvaluationList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserEvaluation(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteUserEvaluations(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllUserEvaluations(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	ElementsList findUserCommentList(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Actuality deleteUserComment(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
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
	ElementsList findCollaboratorList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @param userId
	 * @return
	 */
	Collaborator validateCollaborator(String id, Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Collaborator deleteCollaborator(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteCollaborators(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllCollaborators(Long companyId);
	
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
	ElementsList findContactList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @param userId
	 * @return
	 */
	ContactDetail readContactDetail(String id, Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Contact deleteContact(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteContacts(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllContacts(Long companyId);
	
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
	ElementsList findChatbotList(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @param userId
	 * @return
	 */
	ChatbotDetail readChatbotDetail(String id, Long companyId, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Chatbot deleteChatbot(String id, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteChatbots(List<String> lines, Long companyId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteAllChatbots(Long companyId);
	
}
