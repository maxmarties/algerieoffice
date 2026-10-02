package com.rinitec.algerieoffice.services.user.easylist;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.easylist.EasylistDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;

@Service
public class EasylistService implements IEasylistService {

	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private CompanySearchRepository companySearchRepository;
	private EasylistCompanyRepository easylistCompanyRepository;
	private EasylistDocumentRepository easylistDocumentRepository;
	
	@Autowired
	public EasylistService(PostRepository postRepository, AnnonceRepository annonceRepository, EventRepository eventRepository, EmployeRepository employeRepository, 
			CompanySearchRepository companySearchRepository, EasylistCompanyRepository easylistCompanyRepository, EasylistDocumentRepository easylistDocumentRepository) {
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.companySearchRepository = companySearchRepository;
		this.easylistCompanyRepository = easylistCompanyRepository;
		this.easylistDocumentRepository = easylistDocumentRepository;
	}
	
	@Transactional
	private final EasylistCompany postEasylistCompany(final Long userId, final String easyname, final List<Long> companies) {
		final EasylistCompany easylistCompany = new EasylistCompany();
		easylistCompany.setUserId(userId);
		easylistCompany.setEasyname(easyname);
		easylistCompany.setCompanies(companies);
		easylistCompany.setEasyDate(new DateTime(Date.from(Instant.now())));
		return easylistCompanyRepository.save(easylistCompany);
	}
	
	@Override
	@Transactional
	public EasylistCompany addEasylistCompany(final SearchCompanyForm searchCompanyForm) {
		if(easylistCompanyRepository.countByUserId(searchCompanyForm.getUserId()) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<Long> companies = companySearchRepository.findAllCompanyEasylist(searchCompanyForm);
		if(companies.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(companies.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistCompany(searchCompanyForm.getUserId(), searchCompanyForm.getEasylist(), companies);
	}
	
	@Override
	@Transactional
	public EasylistCompany addEasylistCompany(final Long userId, final String code, final Integer wilaya, final String search, final String easyname) {
		if(easylistCompanyRepository.countByUserId(userId) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<Long> companies = companySearchRepository.findAllCompanyEasylist(code, wilaya, search);
		if(companies.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(companies.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistCompany(userId, easyname, companies);
	}
	
	@Transactional
	private final EasylistDocument postEasylistDocument(final Long userId, final String easyname, final List<UUID> documents, final DocumentType type) {
		final EasylistDocument easylistDocument = new EasylistDocument();
		easylistDocument.setUserId(userId);
		easylistDocument.setEasyname(easyname);
		easylistDocument.setDocuments(documents);
		easylistDocument.setType(type);
		easylistDocument.setEasyDate(new DateTime(Date.from(Instant.now())));
		return easylistDocumentRepository.save(easylistDocument);
	}
	
	@Override
	@Transactional
	public EasylistDocument addEasylistPost(final SearchPostForm searchPostForm) {
		if(easylistDocumentRepository.countByUserId(searchPostForm.getUserId()) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<UUID> documents = postRepository.findAllPostEasylist(searchPostForm);
		if(documents.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(documents.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistDocument(searchPostForm.getUserId(), searchPostForm.getEasylist(), documents, DocumentType.post);
	}
	
	@Override
	@Transactional
	public EasylistDocument addEasylistAnnonce(final SearchAnnonceForm searchAnnonceForm) {
		if(easylistDocumentRepository.countByUserId(searchAnnonceForm.getUserId()) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<UUID> documents = annonceRepository.findAllAnnonceEasylist(searchAnnonceForm);
		if(documents.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(documents.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistDocument(searchAnnonceForm.getUserId(), searchAnnonceForm.getEasylist(), documents, DocumentType.annonce);
	}
	
	@Override
	@Transactional
	public EasylistDocument addEasylistEvent(final SearchEventForm searchEventForm) {
		if(easylistDocumentRepository.countByUserId(searchEventForm.getUserId()) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<UUID> documents = eventRepository.findAllEventEasylist(searchEventForm);
		if(documents.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(documents.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistDocument(searchEventForm.getUserId(), searchEventForm.getEasylist(), documents, DocumentType.event);
	}
	
	@Override
	@Transactional
	public EasylistDocument addEasylistEmploye(final SearchEmployeForm searchEmployeForm) {
		if(easylistDocumentRepository.countByUserId(searchEmployeForm.getUserId()) >= ConstraintesForm.MAX_COUNT_DATA) {
			throw new MaxPlanException("message.plan.easylist");
		}
		final List<UUID> documents = employeRepository.findAllEmployeEasylist(searchEmployeForm);
		if(documents.isEmpty()) {
			throw new EmptyElementException("message.warning.easylist");
		}
		if(documents.size() > ConstraintesForm.MAX_EASYLIST) {
			throw new MaxKeyswordException("lbl.sub.easylist1.1");
		}
		return postEasylistDocument(searchEmployeForm.getUserId(), searchEmployeForm.getEasylist(), documents, DocumentType.employe);
	}
	
}
