package com.rinitec.algerieoffice.services.inbox;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.dao.inbox.TalkRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.feedback.TalkLine;

@Service
public class TalkService implements ITalkService {
	private static final String HREF_NOTICE = "/company/communication/notices";
	private static final String HREF_APPOINTMENT = "/company/communication/appointments";
	private static final String HREF_RATE = "/company/communication/evaluations";
	private static final String HREF_COLLABORATE = "/company/communication/collaborators";
	private static final String HREF_CONTACT = "/company/communication/contacts";
	private static final String HREF_ANALYTIC = "/company/dashboard/analytic";
	private static final String HREF_PARTNER = "/company/communication/partners";
	private static final String HREF_POST = "/company/prospect/quotes";
	private static final String HREF_ANNONCE = "/company/prospect/ads";
	private static final String HREF_EVENT = "/company/prospect/infos";
	private static final String HREF_EMPLOYE = "/company/prospect/jobs";
	private static final String HREF_CHATBOT = "/company/communication/chatbots";
	
	private TalkRepository talkRepository;
	private UserRepository userRepository;
	
	@Autowired
	public TalkService(TalkRepository talkRepository, UserRepository userRepository) {
		this.talkRepository = talkRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countNewTalk(final Long companyId) {
		final int count = (int) talkRepository.countByCompanyIdAndConsulted(companyId, false);
		return count < 100 ? count : 99;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findAllEmailByCompanyId(final Long companyId) {
		return userRepository.findAllEmailByCompanyId(companyId);
	}
	
	private final int toInteger(final TalkType type) {
		switch(type) {
		case notice: return 1;
		case appointment: return 2;
		case rate: return 3;
		case collaborate: return 4;
		case contact: return 5;
		case favorite: return 6;
		case call: return 7;
		case mail: return 8;
		case partner: return 9;
		case post: return 10;
		case annonce: return 11;
		case event: return 12;
		case employe: return 13;
		case chatbot: return 14;
		default: return 0;
		}
	}
	
	private final String toHref(final TalkType type) {
		switch(type) {
		case notice: return HREF_NOTICE;
		case appointment: return HREF_APPOINTMENT;
		case rate: return HREF_RATE;
		case collaborate: return HREF_COLLABORATE;
		case contact: return HREF_CONTACT;
		case favorite: case call: case mail: return HREF_ANALYTIC;
		case partner: return HREF_PARTNER;
		case post: return HREF_POST;
		case annonce: return HREF_ANNONCE;
		case event: return HREF_EVENT;
		case employe: return HREF_EMPLOYE;
		case chatbot: return HREF_CHATBOT;
		default: return null;
		}
	}
	
	@Override
	@Transactional
	public Talk addTalk(final Long companyId, final TalkType type) {
		final Talk talk = new Talk();
		talk.setCompanyId(companyId);
		talk.setType(toInteger(type));
		talk.setLink(toHref(type));
		talk.setTalkedDate(new DateTime(Date.from(Instant.now())));
		return talkRepository.save(talk);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasAllConsulted(final Long companyId) {
		return talkRepository.countByCompanyIdAndConsulted(companyId, false) == 0L;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Talk> findAllTalk(final Long companyId, final Integer page, final Integer rows) {
		return talkRepository.findAllTalk(companyId, PageRequest.of(page - 1, rows));
	}
	
	@Override
	@Transactional
	public void updateAllConsulted(final Long companyId) {
		talkRepository.updateAllConsultedByCompanyId(companyId);
	}
	
	@Override
	@Transactional
	public String getLinkAndConsultedTalk(final Long companyId, final String talkId) {
		try {
			final Optional<Talk> uOptional = talkRepository.findById( UUID.fromString(talkId));
			if(uOptional.isPresent() && uOptional.get().getCompanyId().equals(companyId)) {
				final Talk talk = uOptional.get();
				final String linked = talk.getLink();
				talk.setConsulted(true);
				talkRepository.save(talk);
				return linked;
			}
		} catch (IllegalArgumentException e) {e.printStackTrace();}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findTalkList(final Long companyId, final Integer filter, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = talkRepository.countAllTalkCriteria(companyId, filter);
		final List<TalkLine> lines = countResult == 0L ? new ArrayList<TalkLine>() 
				: talkRepository.findAllTalkCriteria(companyId, filter, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void consultTalk(final String id, final Long companyId) {
		try {
			final Optional<Talk> uOptional = talkRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Talk talk = uOptional.get();
			talk.setConsulted(true);
			talkRepository.save(talk);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteTalk(final String id, final Long companyId) {
		try {
			final Optional<Talk> uOptional = talkRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Talk talk = uOptional.get();
			talkRepository.delete(talk);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteTalks(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) talkRepository.countTalks(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			talkRepository.deleteTalks(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllTalks(final Long companyId) {
		talkRepository.deleteByCompanyId(companyId);
	}
	
}
