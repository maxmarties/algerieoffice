package com.rinitec.algerieoffice.services.admins.marketplace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmAnnonceLine;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmEmployeLine;

@Service
public class AdmMarketplaceService implements IAdmMarketplaceService {

	private AnnonceRepository annonceRepository;
	private EmployeRepository employeRepository;
	
	@Autowired
	public AdmMarketplaceService(AnnonceRepository annonceRepository, EmployeRepository employeRepository) {
		this.annonceRepository = annonceRepository;
		this.employeRepository = employeRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmAnnoncesList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = annonceRepository.countAllAdmAnnonceCriteria(filter, search);
		final List<AdmAnnonceLine> lines = countResult == 0L ? new ArrayList<AdmAnnonceLine>() 
				: annonceRepository.findAllAdmAnnonceCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmEmployesList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = employeRepository.countAllAdmEmployeCriteria(filter, search);
		final List<AdmEmployeLine> lines = countResult == 0L ? new ArrayList<AdmEmployeLine>() 
				: employeRepository.findAllAdmEmployeCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
}
