package com.rinitec.algerieoffice.services.admins.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.inbox.SupportRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IScreenshotService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.feedback.AdmSupportLine;

@Service
public class AdmSupportService implements IAdmSupportService {

	private SupportRepository supportRepository;
	private IScreenshotService screenshotService;
	
	@Autowired
	public AdmSupportService(SupportRepository supportRepository, IScreenshotService screenshotService) {
		this.supportRepository = supportRepository;
		this.screenshotService = screenshotService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllUserSupport(final boolean recevied) {
		return supportRepository.findAllUserSupportCriteria(recevied);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findSupportsList(final boolean recevied, final Long filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = supportRepository.countAllSupportCriteria(recevied, filter, search);
		final List<AdmSupportLine> lines = countResult == 0L ? new ArrayList<AdmSupportLine>() 
				: recevied ? supportRepository.findAllSupportReceviedCriteria(filter, search, sort, rows, page, hasDesc) 
						: supportRepository.findAllSupportSenderCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Support consultSupport(final String id) {
		try {
			final Optional<Support> uOptional = supportRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Support support = uOptional.get();
			support.setConsulted(true);
			supportRepository.save(support);
			return support;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Support deleteSupport(final String id) {
		try {
			final Optional<Support> uOptional = supportRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Support support = uOptional.get();
			if(support.getScreenUUID() != null) {
				screenshotService.deleteScreenshot(support.getScreenUUID());
			}
			supportRepository.delete(support);
			return support;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteSupports(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) supportRepository.countSupport(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> screensUUID = supportRepository.findAllScreensUUID(linesUUID);
			supportRepository.deleteSupports(linesUUID);
			screenshotService.deleteScreenshots(screensUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
