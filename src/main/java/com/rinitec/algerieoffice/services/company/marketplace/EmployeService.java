package com.rinitec.algerieoffice.services.company.marketplace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeLocation;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.EmployeForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.marketplace.EmployeLine;

@Service
public class EmployeService implements IEmployeService {
	
	private EmployeRepository employeRepository;
	private EmployeDetailRepository employeDetailRepository;
	private EmployeLocationRepository employeLocationRepository;
	
	@Autowired
	public EmployeService(EmployeRepository employeRepository, EmployeDetailRepository employeDetailRepository,
			EmployeLocationRepository employeLocationRepository) {
		this.employeRepository = employeRepository;
		this.employeDetailRepository = employeDetailRepository;
		this.employeLocationRepository = employeLocationRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasMaxEmploye(final Long companyId) {
		final long count = employeRepository.countByCompanyId(companyId);
		return count >= ConstraintesForm.MAX_COUNT_DATA;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countTrashedEmploye(final Long companyId) {
		return employeRepository.countByCompanyIdAndHasTrashed(companyId, true);
	}
	
	private final EmployeForm parseEmployeForm(final Employe employe, final EmployeDetail employeDetail, final List<Integer> locations) {
		final EmployeForm employeForm = new EmployeForm();
		employeForm.setId(employe.getId().toString());
		employeForm.setCompanyId(employe.getCompanyId());
		employeForm.setTitle(employe.getTitle());
		employeForm.setIdentify(employe.getIdentify());
		employeForm.setContract(employe.getContract());
		employeForm.setExpiredDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate()));
		employeForm.setDescription(employe.getDescription());
		employeForm.setKeysword(employe.getKeysword());
		employeForm.setUrlExtern(employeDetail.getUrlExtern());
		employeForm.setDetail(new String(employeDetail.getDetail()));
		employeForm.setDomaine(employeDetail.getDomaine());
		employeForm.setDiscoverType(employeDetail.getDiscoverType());
		employeForm.setDiscoverValue(employeDetail.getDiscoverValue());
		employeForm.setHasPublished(employe.getHasPublished());
		employeForm.setLocations(locations);
		return employeForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public EmployeForm readEmployeForm(final String id, final Long companyId) {
		try {
			final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Employe employe = uOptional.get();
				final EmployeDetail employeDetail = employeDetailRepository.findById(employe.getId()).get();
				final List<Integer> locations = employeLocationRepository.findLocationsByEmployeId(employe.getId());
				return parseEmployeForm(employe, employeDetail, locations);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Employe postEmploye(final Employe employe, final EmployeForm employeForm, final Long userId) {
		employe.setTitle(employeForm.getTitle());
		employe.setIdentify(employeForm.getIdentify());
		employe.setContract(employeForm.getContract());
		employe.setExpiredDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(employeForm.getExpiredDate()));
		employe.setDescription(employeForm.getDescription());
		employe.setKeysword(employeForm.getKeysword());
		employe.setAutorId(userId);
		employe.setModifiedDate(new DateTime(Date.from(Instant.now())));
		employe.setHasPublished(employeForm.getHasPublished());
		return employeRepository.save(employe);
	}
	
	@Transactional
	private final EmployeDetail postEmployeDetail(final EmployeDetail employeDetail, final EmployeForm employeForm) {
		employeDetail.setDetail(employeForm.getDetail().getBytes());
		employeDetail.setUrlExtern(!StringUtils.isEmpty(employeForm.getUrlExtern()) ? employeForm.getUrlExtern() : null);
		employeDetail.setDomaine(employeForm.getDomaine());
		employeDetail.setDiscoverType(employeForm.getDiscoverType());
		employeDetail.setDiscoverValue(employeForm.getDiscoverValue());
		return employeDetailRepository.save(employeDetail);
	}
	
	@Transactional
	private final void createEmployeLocations(final UUID employeId, final List<Integer> locations) {
		final List<EmployeLocation> employeLocations = new ArrayList<EmployeLocation>();
		for (final Integer location : locations) {
			final EmployeLocation employeLocation = new EmployeLocation();
			employeLocation.setEmployeUUID(employeId);
			employeLocation.setLocation(location);
			employeLocations.add(employeLocation);
		}
		employeLocationRepository.saveAll(employeLocations);
	}
	
	@Override
	@Transactional
	public Employe addEmploye(final EmployeForm employeForm, final int maxKeysword, final Long userId) {
		if(employeRepository.findIdByIdentify(employeForm.getCompanyId(), employeForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(!StringUtils.isEmpty(employeForm.getKeysword()) && employeForm.getKeysword().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(!StringUtils.isEmpty(employeForm.getUrlExtern()) && employeDetailRepository.existsByUrlExtern(employeForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Employe employe = postEmploye(new Employe(employeForm.getCompanyId()), employeForm, userId);
		postEmployeDetail(new EmployeDetail(employe), employeForm);
		createEmployeLocations(employe.getId(), employeForm.getLocations());
		return employe;
	}
	
	@Transactional
	private final void updateEmployeLocations(final UUID employeId, final List<Integer> locations) {
		employeLocationRepository.deleteByEmployeUUID(employeId);
		createEmployeLocations(employeId, locations);
	}
	
	@Override
	@Transactional
	public Employe updateEmploye(final EmployeForm employeForm, final int maxKeysword, final Long userId) {
		final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(employeForm.getId()));
		if(uOptional.isPresent() && employeForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Employe employe = uOptional.get();
			final EmployeDetail employeDetail = employeDetailRepository.findById(employe.getId()).get();
			if(!employeForm.getIdentify().equalsIgnoreCase(employe.getIdentify()) 
					&& employeRepository.findIdByIdentify(employeForm.getCompanyId(), employeForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(!StringUtils.isEmpty(employeForm.getKeysword()) && employeForm.getKeysword().split(",").length > maxKeysword) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			if(!StringUtils.isEmpty(employeForm.getUrlExtern()) && !employeForm.getUrlExtern().equalsIgnoreCase(employeDetail.getUrlExtern()) 
					&& employeDetailRepository.existsByUrlExtern(employeForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			postEmploye(employe, employeForm, userId);
			postEmployeDetail(employeDetail, employeForm);
			if(employeForm.isUpdateLocations()) {
				updateEmployeLocations(employe.getId(), employeForm.getLocations());
			}
			return employe;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEmployesList(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = employeRepository.countAllEmployeCriteria(companyId, filter, search);
		final List<EmployeLine> lines = countResult == 0L ? new ArrayList<EmployeLine>() 
				: employeRepository.findAllEmployeCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Employe publishEmploye(final String id, final Long companyId) {
		try {
			final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Employe employe = uOptional.get();
			employe.setHasPublished(!employe.getHasPublished());
			return employeRepository.save(employe);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Employe trashEmploye(final String id, final Long companyId) {
		try {
			final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Employe employe = uOptional.get();
			employe.setHasTrashed(true);
			return employeRepository.save(employe);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashEmployes(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) employeRepository.countEmployes(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			employeRepository.trashEmployes(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashAllEmployes(final Long companyId) {
		employeRepository.trashAllEmployes(companyId);
	}
	
}
