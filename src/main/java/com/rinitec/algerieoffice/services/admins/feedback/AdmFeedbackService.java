package com.rinitec.algerieoffice.services.admins.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.RateRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmBlockLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmLockLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmRateLine;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmReportLine;

@Service
public class AdmFeedbackService implements IAdmFeedbackService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private ReportRepository reportRepository;
	private RateRepository rateRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public AdmFeedbackService(UserRepository userRepository, CompanyRepository companyRepository, ReportRepository reportRepository, 
			RateRepository rateRepository, IEnvelopeService envelopeService) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.reportRepository = reportRepository;
		this.rateRepository = rateRepository;
		this.envelopeService = envelopeService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmReportList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = reportRepository.countAllReportAdmin(filter, search);
		final List<AdmReportLine> lines = countResult == 0L ? new ArrayList<AdmReportLine>() 
				: reportRepository.findAllReportAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Report validateReport(final String id) {
		try {
			final Optional<Report> uOptional = reportRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Report report = uOptional.get();
			report.setApprouved(true);
			return reportRepository.save(report);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Company lockCompnyReport(final Long companyId, final boolean hasLocked) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		if(!uOptional.isPresent() || uOptional.get().isLocked() == hasLocked) {
			throw new NotFoundException("message.error.notfound");
		}
		final Company company = uOptional.get();
		company.setLocked(hasLocked);
		return companyRepository.save(company);
	}
	
	@Override
	@Transactional
	public Company disableCompanyReport(final Long companyId) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		if(!uOptional.isPresent() || !uOptional.get().isEnabled()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Company company = uOptional.get();
		company.setEnabled(false);
		return companyRepository.save(company);
	}
	
	@Override
	@Transactional
	public Company diactivateCompanyReport(final Long companyId) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		if(!uOptional.isPresent() || !uOptional.get().isActive()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Company company = uOptional.get();
		company.setActive(false);
		return companyRepository.save(company);
	}
	
	@Override
	@Transactional
	public Report deleteReport(final String id) {
		try {
			final Optional<Report> uOptional = reportRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Report report = uOptional.get();
			if(report.getFileUUID() != null) {
				envelopeService.deleteEnvelope(report.getFileUUID());
			}
			reportRepository.delete(report);
			return report;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteReports(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) reportRepository.countReports(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> filesUUID = reportRepository.findAllFileUUIDById(linesUUID);
			if(!filesUUID.isEmpty()) {
				envelopeService.deleteAll(filesUUID);
			}
			reportRepository.deleteReports(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmRateList(final Integer filter, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = rateRepository.countAllRateAdmin(filter, search);
		final List<AdmRateLine> lines = countResult == 0L ? new ArrayList<AdmRateLine>() 
				: rateRepository.findAllRateAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Rate validateRate(final String id) {
		try {
			final Optional<Rate> uOptional = rateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Rate rate = uOptional.get();
			rate.setApprouved(true);
			return rateRepository.save(rate);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public User lockUserRate(final Long userId, final boolean hasLocked) {
		final Optional<User> uOptional = userRepository.findById(userId);
		if(!uOptional.isPresent() || uOptional.get().isLocked() == hasLocked) {
			throw new NotFoundException("message.error.notfound");
		}
		final User user = uOptional.get();
		user.setLocked(hasLocked);
		return userRepository.save(user);
	}
	
	@Override
	@Transactional
	public Rate deleteRate(final String id) {
		try {
			final Optional<Rate> uOptional = rateRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Rate rate = uOptional.get();
			rateRepository.delete(rate);
			return rate;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteRates(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) rateRepository.countRates(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			rateRepository.deleteRates(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmLockList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = reportRepository.countAllLockAdmin(search);
		final List<AdmLockLine> lines = countResult == 0L ? new ArrayList<AdmLockLine>() 
				: reportRepository.findAllLockAdmin(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void unlockCompniesReport(final List<Long> lines) {
		companyRepository.updateLockedByIds(false, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmBlockList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = rateRepository.countAllBlockAdmin(search);
		final List<AdmBlockLine> lines = countResult == 0L ? new ArrayList<AdmBlockLine>() 
				: rateRepository.findAllBlockAdmin(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void unlockUsersReport(final List<Long> lines) {
		userRepository.updateLockedByIds(false, lines);
	}
	
}
