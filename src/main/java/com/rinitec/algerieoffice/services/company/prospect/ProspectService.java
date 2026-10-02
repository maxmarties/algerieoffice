package com.rinitec.algerieoffice.services.company.prospect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.prospect.ProspectDetail;
import com.rinitec.algerieoffice.web.modal.company.prospect.ProspectLine;

@Service
public class ProspectService implements IProspectService {

	private GuestDocumentRepository guestDocumentRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private IFilereaderService filereaderService;
	
	@Autowired
	public ProspectService(GuestDocumentRepository guestDocumentRepository, PostRepository postRepository, 
			AnnonceRepository annonceRepository, EventRepository eventRepository, EmployeRepository employeRepository, 
			IFilereaderService filereaderService) {
		this.guestDocumentRepository = guestDocumentRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.filereaderService = filereaderService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findQuoteList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = guestDocumentRepository.countAllQuoteDocumentCriteria(companyId, filter, search);
		final List<ProspectLine> lines = countResult == 0L ? new ArrayList<ProspectLine>() 
				: guestDocumentRepository.findAllQuoteDocumentCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ProspectDetail readQuoteDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final GuestDocument guestDocument = uOptional.get();
				if(guestDocument.getType().equals(DocumentType.post)) {
					final Post post = postRepository.findById(guestDocument.getDocumentId()).get();
					final ProspectDetail quoteDetail = new ProspectDetail(guestDocument, post);
					if(!guestDocument.isConsulted()) {
						guestDocument.setConsulted(true);
						guestDocument.setConsultedBy(userId);
						guestDocumentRepository.save(guestDocument);
					}
					return quoteDetail;
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdsList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = guestDocumentRepository.countAllAdsDocumentCriteria(companyId, filter, search);
		final List<ProspectLine> lines = countResult == 0L ? new ArrayList<ProspectLine>() 
				: guestDocumentRepository.findAllAdsDocumentCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ProspectDetail readAdsDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final GuestDocument guestDocument = uOptional.get();
				if(guestDocument.getType().equals(DocumentType.annonce)) {
					final Annonce annonce = annonceRepository.findById(guestDocument.getDocumentId()).get();
					final ProspectDetail adsDetail = new ProspectDetail(guestDocument, annonce);
					if(!guestDocument.isConsulted()) {
						guestDocument.setConsulted(true);
						guestDocument.setConsultedBy(userId);
						guestDocumentRepository.save(guestDocument);
					}
					return adsDetail;
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findInfoList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = guestDocumentRepository.countAllInfoDocumentCriteria(companyId, filter, search);
		final List<ProspectLine> lines = countResult == 0L ? new ArrayList<ProspectLine>() 
				: guestDocumentRepository.findAllInfoDocumentCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ProspectDetail readInfoDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final GuestDocument guestDocument = uOptional.get();
				if(guestDocument.getType().equals(DocumentType.event)) {
					final Event event = eventRepository.findById(guestDocument.getDocumentId()).get();
					final ProspectDetail adsDetail = new ProspectDetail(guestDocument, event);
					if(!guestDocument.isConsulted()) {
						guestDocument.setConsulted(true);
						guestDocument.setConsultedBy(userId);
						guestDocumentRepository.save(guestDocument);
					}
					return adsDetail;
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findJobList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = guestDocumentRepository.countAllJobDocumentCriteria(companyId, filter, search);
		final List<ProspectLine> lines = countResult == 0L ? new ArrayList<ProspectLine>() 
				: guestDocumentRepository.findAllJobDocumentCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public ProspectDetail readJobDetail(final String id, final Long companyId, final Long userId) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final GuestDocument guestDocument = uOptional.get();
				if(guestDocument.getType().equals(DocumentType.employe)) {
					final Employe employe = employeRepository.findById(guestDocument.getDocumentId()).get();
					final ProspectDetail adsDetail = new ProspectDetail(guestDocument, employe);
					if(!guestDocument.isConsulted()) {
						guestDocument.setConsulted(true);
						guestDocument.setConsultedBy(userId);
						guestDocumentRepository.save(guestDocument);
					}
					return adsDetail;
				}
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public GuestDocument validateProspect(final String id, final Long companyId, final Long userId, final DocumentType type) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId()) 
					|| !uOptional.get().getType().equals(type)) {
				throw new NotFoundException("message.error.notfound");
			}
			final GuestDocument guestDocument = uOptional.get();
			guestDocument.setConsulted(true);
			guestDocument.setConsultedBy(userId);
			return guestDocumentRepository.save(guestDocument);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public GuestDocument deleteProspect(final String id, final Long companyId, final DocumentType type) {
		try {
			final Optional<GuestDocument> uOptional = guestDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId()) 
					|| !uOptional.get().getType().equals(type)) {
				throw new NotFoundException("message.error.notfound");
			}
			final GuestDocument guestDocument = uOptional.get();
			if(guestDocument.getFileUUID() != null) {
				filereaderService.deleteFilereader(guestDocument.getFileUUID());
			}
			guestDocumentRepository.delete(guestDocument);
			return guestDocument;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteProspects(final List<String> lines, final Long companyId, final DocumentType type) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) guestDocumentRepository.countGuestDocuments(companyId, type, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDById(companyId, type, linesUUID);
			filereaderService.deleteAllFilereaders(linesFiles);
			guestDocumentRepository.deleteGuestDocuments(type, linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllProspects(final Long companyId, final DocumentType type) {
		final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByCompanyId(companyId, type);
		filereaderService.deleteAllFilereaders(linesFiles);
		guestDocumentRepository.deleteAllGuestDocuments(companyId, type);
	}
	
}
