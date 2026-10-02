package com.rinitec.algerieoffice.services.company.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectLine;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectList;

@Service
public class DetectService implements IDetectService {

	private AccessCompanyRepository accessCompanyRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public DetectService(AccessCompanyRepository accessCompanyRepository, ActiveUserStore activeUserStore) {
		this.accessCompanyRepository = accessCompanyRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findDetectList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = accessCompanyRepository.countAllDetectCriteria(companyId, filter, search);
		final List<DetectLine> lines = countResult == 0L ? new ArrayList<DetectLine>() 
				: accessCompanyRepository.findAllDetectCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteDetect(final String id, final Long companyId) {
		try {
			final Optional<AccessCompany> uOptional = accessCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final AccessCompany accessCompany = uOptional.get();
			accessCompanyRepository.delete(accessCompany);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteDetects(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) accessCompanyRepository.countAccessCompany(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			accessCompanyRepository.deleteAccessCompanies(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllDetects(final Long companyId) {
		accessCompanyRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DetectList findDetectAccessList(final Long companyId, final int limit) {
		final DetectList list = new DetectList();
		final List<DetectAccess> lines = accessCompanyRepository.findDetectAccessListCriteria(companyId, limit);
		if(!lines.isEmpty()) {
			for (final DetectAccess line : lines) {
				final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
				line.updateOnline(hasOnline);
				list.addLine(line);
			}
		}
		return list;
	}
	
}
