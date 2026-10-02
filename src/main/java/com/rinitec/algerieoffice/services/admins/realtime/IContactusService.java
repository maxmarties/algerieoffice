package com.rinitec.algerieoffice.services.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.ujson.ChatbotResponse;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.register.ContactsForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotDetail;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmContactDetail;

public interface IContactusService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param contactsForm
	 * @return
	 */
	Contactus addContactus(ContactsForm contactsForm);
	
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
	ElementsList findContactusList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	AdmContactDetail readAdmContactDetail(String id);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Contactus deleteContactus(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteContactsus(List<String> lines) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param chatbotResponse
	 * @return
	 */
	Chatbot addChatbot(ChatbotResponse chatbotResponse);
	
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
	ElementsList findAdmChatbotList(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @return
	 */
	AdmChatbotDetail readAdmChatbotDetail(String id, Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	Chatbot deleteChatbot(String id) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteChatbots(List<String> lines) throws NotFoundException, InvalidResourceException;
	
}
