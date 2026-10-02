package com.rinitec.algerieoffice.services.admins.easylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.easylist.AdmEasylistCompany;
import com.rinitec.algerieoffice.web.modal.admins.easylist.AdmEasylistDocument;

@Service
public class AdmEasylistService implements IAdmEasylistService {

	private EasylistCompanyRepository easylistCompanyRepository;
	private EasylistDocumentRepository easylistDocumentRepository;
	
	@Autowired
	public AdmEasylistService(EasylistCompanyRepository easylistCompanyRepository, EasylistDocumentRepository easylistDocumentRepository) {
		this.easylistCompanyRepository = easylistCompanyRepository;
		this.easylistDocumentRepository = easylistDocumentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmEasylistCompanyList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = easylistCompanyRepository.countAllAdmEasylistCompanyCriteria(filter, search);
		final List<AdmEasylistCompany> lines = countResult == 0L ? new ArrayList<AdmEasylistCompany>() 
				: easylistCompanyRepository.findAllAdmEasylistCompanyCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public EasylistCompany deleteAdmEasylistCompany(final String id) {
		try {
			final Optional<EasylistCompany> uOptional = easylistCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
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
	public void deleteAdmEasylistCompanies(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) easylistCompanyRepository.countAllEasylistCompanies(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			easylistCompanyRepository.deleteEasylistCompanies(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmEasylistDocumentList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = easylistDocumentRepository.countAllAdmEasylistDocumentCriteria(filter, search);
		final List<AdmEasylistDocument> lines = countResult == 0L ? new ArrayList<AdmEasylistDocument>() 
				: easylistDocumentRepository.findAllAdmEasylistDocumentCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public EasylistDocument deleteAdmEasylistDocument(final String id) {
		try {
			final Optional<EasylistDocument> uOptional = easylistDocumentRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
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
	public void deleteAdmEasylistDocuments(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) easylistDocumentRepository.countAllEasylistDocuments(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			easylistDocumentRepository.deleteAllEasylistDocuments(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
