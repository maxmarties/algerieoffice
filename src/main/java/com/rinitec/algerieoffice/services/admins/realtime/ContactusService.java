package com.rinitec.algerieoffice.services.admins.realtime;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ChatbotRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ContactusRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.ujson.ChatbotResponse;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.register.ContactsForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotDetail;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmContactDetail;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmContactLine;

@Service
public class ContactusService implements IContactusService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private ContactusRepository contactusRepository;
	private ChatbotRepository chatbotRepository;
	
	@Autowired
	public ContactusService(UserRepository userRepository, CompanyRepository companyRepository, ContactusRepository contactusRepository, 
			ChatbotRepository chatbotRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.contactusRepository = contactusRepository;
		this.chatbotRepository = chatbotRepository;
	}
	
	@Override
	@Transactional
	public Contactus addContactus(final ContactsForm contactsForm) {
		final Contactus contactus = new Contactus();
		contactus.setUserId(contactsForm.getUserId());
		contactus.setObject(contactsForm.getObject());
		contactus.setFirstName(contactsForm.getFirstname());
		contactus.setLastName(contactsForm.getLastname());
		contactus.setPhone(contactsForm.getPhone());
		contactus.setEmail(contactsForm.getEmail());
		contactus.setCompany(contactsForm.getEmail());
		contactus.setMessage(contactsForm.getMessage());
		contactus.setPostedDate(new DateTime(Date.from(Instant.now())));
		return contactusRepository.save(contactus);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findContactusList(final Boolean filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = contactusRepository.countAllContactusCriteria(filter, search);
		final List<AdmContactLine> lines = countResult == 0L ? new ArrayList<AdmContactLine>() 
				: contactusRepository.findAllContactusCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional
	private final Contactus updateConsultedContactus(final Contactus contactus) {
		if(!contactus.isConsulted()) {
			contactus.setConsulted(true);
			return contactusRepository.save(contactus);
		}
		return contactus;
	}
	
	@Transactional(readOnly = true)
	private final User readUserContact(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final String readTradenameContact(final Long companyId) {
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional
	public AdmContactDetail readAdmContactDetail(final String id) {
		try {
			final Optional<Contactus> uOptional = contactusRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Contactus contactus = updateConsultedContactus(uOptional.get());
				final User user = contactus.getUserId() != null ? readUserContact(contactus.getUserId()) : null;
				final String tradeame = user != null && user.getCompanyId() != null ? readTradenameContact(user.getCompanyId()) : null;
				return new AdmContactDetail(contactus, user, tradeame);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Contactus deleteContactus(final String id) {
		try {
			final Optional<Contactus> uOptional = contactusRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Contactus contactus = uOptional.get();
			contactusRepository.delete(contactus);
			return contactus;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteContactsus(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) contactusRepository.countContactus(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			contactusRepository.deleteContactus(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Chatbot addChatbot(final ChatbotResponse chatbotResponse) {
		final Chatbot chatbot = new Chatbot();
		chatbot.setCompanyId(chatbotResponse.getCompanyId());
		chatbot.setDomaine(chatbotResponse.getDomaine());
		chatbot.setDiscute(chatbotResponse.isDiscute());
		chatbot.setAccount(chatbotResponse.getAccount());
		chatbot.setEmail(!StringUtils.isEmpty(chatbotResponse.getEmail()) ? chatbotResponse.getEmail() : null);
		chatbot.setMessage(!StringUtils.isEmpty(chatbotResponse.getMessage()) ? chatbotResponse.getMessage() : null);
		chatbot.setPostedDate(new DateTime(Date.from(Instant.now())));
		return chatbotRepository.save(chatbot);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmChatbotList(final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = chatbotRepository.countAllAdmChatbotCriteria(filter, search);
		final List<AdmChatbotLine> lines = countResult == 0L ? new ArrayList<AdmChatbotLine>() 
				: chatbotRepository.findAllAdmChatbotCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional
	private final Chatbot updateConsultedChatbot(final Chatbot chatbot, final Long userId) {
		if(!chatbot.isConsulted()) {
			chatbot.setConsulted(true);
			chatbot.setConsultedBy(userId);
			return chatbotRepository.save(chatbot);
		}
		return chatbot;
	}
	
	@Override
	@Transactional
	public AdmChatbotDetail readAdmChatbotDetail(final String id, final Long userId) {
		try {
			final Optional<Chatbot> uOptional = chatbotRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && (uOptional.get().getCompanyId() == null || uOptional.get().getAccount() != null)) {
				final Chatbot chatbot = updateConsultedChatbot(uOptional.get(), userId);
				final Company company = chatbot.getCompanyId() != null ? companyRepository.findById(chatbot.getCompanyId()).get() : null;
				return new AdmChatbotDetail(chatbot, company);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Chatbot deleteChatbot(final String id) {
		try {
			final Optional<Chatbot> uOptional = chatbotRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || (uOptional.get().getCompanyId() != null && uOptional.get().getAccount() == null)) {
				throw new NotFoundException("message.error.notfound");
			}
			final Chatbot chatbot = uOptional.get();
			chatbotRepository.delete(chatbot);
			return chatbot;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteChatbots(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) chatbotRepository.countChatbots(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			chatbotRepository.deleteChatbots(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
