package com.rinitec.algerieoffice.services.user.easylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistCompanyLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEventLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistPostLine;

@Service
public class EasylistDetailService implements IEasylistDetailService {

	private EasylistCompanyRepository easylistCompanyRepository;
	private EasylistDocumentRepository easylistDocumentRepository;
	
	@Autowired
	public EasylistDetailService(EasylistCompanyRepository easylistCompanyRepository, EasylistDocumentRepository easylistDocumentRepository) {
		this.easylistCompanyRepository = easylistCompanyRepository;
		this.easylistDocumentRepository = easylistDocumentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistCompanies(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = easylistCompanyRepository.countAllEasylistCompaniesCriteria(userId, filter, search);
		final List<EasylistLine> lines = countResult == 0L ? new ArrayList<EasylistLine>() 
				: easylistCompanyRepository.findAllEasylistCompaniesCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public EasylistCompany deleteEasylistCompany(final String id, final Long userId) {
		try {
			final Optional<EasylistCompany> uOptional = easylistCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final EasylistCompany easylistCompany = uOptional.get();
			easylistCompanyRepository.delete(easylistCompany);
			return easylistCompany;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteEasylistCompanies(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) easylistCompanyRepository.countEasylistCompanies(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			easylistCompanyRepository.deleteEasylistCompanies(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllEasylistCompanies(final Long userId) {
		easylistCompanyRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EasylistCompany readEasylistCompany(final String id, final Long userId) {
		try {
			final Optional<EasylistCompany> uOptional = easylistCompanyRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && uOptional.get().getUserId().equals(userId)) {
				return uOptional.get();
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistCompany(final String id, final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		try {
			final Optional<EasylistCompany> uOptional = easylistCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<Long> companies = uOptional.get().getCompanies();
			final Long countResult = easylistCompanyRepository.countAllEasylistCompanyCriteria(companies, filter, search);
			final List<EasylistCompanyLine> lines = countResult == 0L ? new ArrayList<EasylistCompanyLine>() 
					: easylistCompanyRepository.findAllEasylistCompanyCriteria(companies, filter, search, sort, rows, page, hasDesc);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistDocuments(final Long userId, final DocumentType type, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = easylistDocumentRepository.countAllEasylistDocumentsCriteria(userId, type, filter, search);
		final List<EasylistLine> lines = countResult == 0L ? new ArrayList<EasylistLine>() 
				: easylistDocumentRepository.findAllEasylistDocumentsCriteria(userId, type, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public EasylistDocument deleteEasylistDocument(final String id, final Long userId, final DocumentType type) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) || !type.equals(uOptional.get().getType())) {
				throw new NotFoundException("message.error.notfound");
			}
			final EasylistDocument easylistDocument = uOptional.get();
			easylistDocumentRepository.delete(easylistDocument);
			return easylistDocument;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteEasylistDocuments(final List<String> lines, final Long userId, final DocumentType type) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) easylistDocumentRepository.countEasylistDocuments(userId, type, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			easylistDocumentRepository.deleteEasylistDocuments(linesUUID, type);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllEasylistDocuments(final Long userId, final DocumentType type) {
		easylistDocumentRepository.deleteByUserIdAndType(userId, type);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EasylistDocument readEasylistDocument(final String id, final Long userId, final DocumentType type) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && uOptional.get().getUserId().equals(userId) && uOptional.get().getType().equals(type)) {
				return uOptional.get();
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistPost(final String id, final Long userId, final String filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) || !uOptional.get().getType().equals(DocumentType.post)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> documents = uOptional.get().getDocuments();
			final Long countResult = easylistDocumentRepository.countAllEasylistPostCriteria(documents, filter, search);
			final List<EasylistPostLine> lines = countResult == 0L ? new ArrayList<EasylistPostLine>() 
					: easylistDocumentRepository.findAllEasylistPostCriteria(documents, filter, search, sort, rows, page, hasDesc);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistAnnonce(final String id, final Long userId, final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) || !uOptional.get().getType().equals(DocumentType.annonce)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> documents = uOptional.get().getDocuments();
			final Long countResult = easylistDocumentRepository.countAllEasylistAnnonceCriteria(documents, filter, search);
			final List<EasylistAnnonceLine> lines = countResult == 0L ? new ArrayList<EasylistAnnonceLine>() 
					: easylistDocumentRepository.findAllEasylistAnnonceCriteria(documents, filter, search, sort, rows, page, hasDesc);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistEvent(final String id, final Long userId, final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) || !uOptional.get().getType().equals(DocumentType.event)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> documents = uOptional.get().getDocuments();
			final Long countResult = easylistDocumentRepository.countAllEasylistEventCriteria(documents, filter, search);
			final List<EasylistEventLine> lines = countResult == 0L ? new ArrayList<EasylistEventLine>() 
					: easylistDocumentRepository.findAllEasylistEventCriteria(documents, filter, search, sort, rows, page, hasDesc);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEasylistEmploye(final String id, final Long userId, final Integer filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) || !uOptional.get().getType().equals(DocumentType.employe)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> documents = uOptional.get().getDocuments();
			final Long countResult = easylistDocumentRepository.countAllEasylistEmployeCriteria(documents, filter, search);
			final List<EasylistEmployeLine> lines = countResult == 0L ? new ArrayList<EasylistEmployeLine>() 
					: easylistDocumentRepository.findAllEasylistEmployeCriteria(documents, filter, search, sort, rows, page, hasDesc);
			return new ElementsList(countResult, lines);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
