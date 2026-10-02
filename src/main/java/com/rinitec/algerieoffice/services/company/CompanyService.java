package com.rinitec.algerieoffice.services.company;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;

@Service
public class CompanyService implements ICompanyService {
	
	private CompanyRepository companyRepository;
	private CompanyAccountRepository companyAccountRepository;
	
	@Autowired
	public CompanyService(CompanyRepository companyRepository, CompanyAccountRepository companyAccountRepository) {
		this.companyRepository = companyRepository;
		this.companyAccountRepository = companyAccountRepository;
	}
	
	@Override
	@Transactional
	public void incrementVisits(final Long companyId) {
		companyAccountRepository.incrementVisits(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findCompanymail(final Long companyId) {
		final Optional<String> uOptional = companyRepository.findCompanymailById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
}
