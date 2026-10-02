package com.rinitec.algerieoffice.services.company.profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.data.IdentityHistoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyIdentityRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.data.IdentityHistory;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyIdentity;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.datas.IdentityResponseForm;
import com.rinitec.algerieoffice.web.form.company.profile.IdentityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.data.IdentityDetail;
import com.rinitec.algerieoffice.web.modal.admins.data.IdentityLine;
import com.rinitec.algerieoffice.web.modal.company.profile.IdentityState;

@Service
public class IdentityService implements IIdentityService {

	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyIdentityRepository companyIdentityRepository;
	private ActivityRepository activityRepository;
	private IdentityHistoryRepository identityHistoryRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public IdentityService(CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, CompanyIdentityRepository companyIdentityRepository, 
			ActivityRepository activityRepository, IdentityHistoryRepository identityHistoryRepository, IAvatarService avatarService) {
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyIdentityRepository = companyIdentityRepository;
		this.activityRepository = activityRepository;
		this.identityHistoryRepository = identityHistoryRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByCompanyId(final Long companyId) {
		return companyIdentityRepository.existsById(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByURL(final String url) {
		return companySeoRepository.existsByUrl(url);
	}
	
	@Override
	@Transactional(readOnly = true)
	public IdentityState readIdentityState(final Long companyId) {
		final Optional<CompanyIdentity> uOptional = companyIdentityRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final CompanyIdentity companyIdentity = uOptional.get();
			return new IdentityState(true, companyIdentity.getConsulted(), companyIdentity.getRequestedDate());
		} 
		return new IdentityState(false, false, null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public IdentityForm readIdentityForm(final Long companyId) {
		final IdentityForm identityForm = new IdentityForm();
		final Company company = companyRepository.findById(companyId).get();
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		final List<String> codes = new ArrayList<String>();
		identityForm.setId(companyId);
		identityForm.setDenomination(company.getDenomination());
		identityForm.setTradename(company.getTradename());
		if(company.getBuildDate() != null) {
			identityForm.setBuildDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(company.getBuildDate()));
		}
		identityForm.setActivity(activities.get(0).getCode());
		if(activities.size() > 1) {
			for(int i = 1; i < activities.size(); i++) {
				codes.add(activities.get(i).getCode());
			}
		}
		identityForm.setActivities(codes);
		if(!company.isEnabled()) {
			final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
			if(uOptional.isPresent()) {
				final String url = uOptional.get();
				identityForm.setUrl(url);
				identityForm.setCheckedURL(url);
			}
		}
		return identityForm;
	}
	
	private final boolean isDuplicatedCode(final String code, final List<String> codes) {
		final Set<String> set = new HashSet<String>(codes);
		return set.size() < codes.size() || codes.contains(code);
	}
	
	@Transactional
	private final boolean isNotFoundActivity(final String code, final List<String> codes) {
		if(!activityRepository.existsByCode(code)) {
			return true;
		}
		return !codes.isEmpty() && activityRepository.countAllByCodes(codes) < codes.size();
	}
	
	@Transactional
	private final List<Activity> readAllActivities(final String code, final List<String> codes) {
		final List<Activity> activities = new ArrayList<Activity>(Arrays.asList(activityRepository.findByCode(code)));
		if(!codes.isEmpty()) {
			activities.addAll(activityRepository.findAllByCodes(codes));
		}
		return activities;
	}
	
	@Transactional
	private final Company updateCompany(final IdentityForm identityForm) {
		final Company company = companyRepository.findById(identityForm.getId()).get();
		company.setDenomination(identityForm.getDenomination());
		company.setTradename(identityForm.getTradename());
		company.setActivities(readAllActivities(identityForm.getActivity(), identityForm.getActivities()));
		company.setBuildDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(identityForm.getBuildDate()));
		company.setEnabled(false);
		return companyRepository.save(company);
	}
	
	@Transactional
	private final CompanyIdentity createOrUpdate(final Long companyId, final Long userId) {
		final Optional<CompanyIdentity> uOptional = companyIdentityRepository.findById(companyId);
		final CompanyIdentity companyIdentity = uOptional.isPresent() ? uOptional.get() : new CompanyIdentity(companyId);
		companyIdentity.setUserId(userId);
		companyIdentity.setConsulted(false);
		companyIdentity.setRequestedDate(new DateTime(Date.from(Instant.now())));
		return companyIdentityRepository.save(companyIdentity);
	}
	
	@Override
	@Transactional
	public CompanyIdentity updateCompanyIdentity(final IdentityForm identityForm, final Long userId) {
		if(isDuplicatedCode(identityForm.getActivity(), identityForm.getActivities())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(isNotFoundActivity(identityForm.getActivity(), identityForm.getActivities())) {
			throw new NotFoundException("message.input.notfound");
		}
		if(!StringUtils.isEmpty(identityForm.getUrl()) && !identityForm.getUrl().equalsIgnoreCase(identityForm.getCheckedURL())) {
			if(companySeoRepository.existsByUrl(identityForm.getUrl())) {
				throw new UrlUnavailableException("message.error.url");
			}
			companySeoRepository.updateUrlById(identityForm.getUrl(), identityForm.getId());
		}
		updateCompany(identityForm);
		avatarService.postOrUpdate(identityForm.getFile(), identityForm.getId(), AvatarType.identity);
		return createOrUpdate(identityForm.getId(), userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findIdentitiesList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = companyIdentityRepository.countAllIdentityCriteria(search);
		final List<IdentityLine> lines = companyIdentityRepository.findAllIdentityCriteria(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	private final List<String> readCodes(final List<Activity> activities) {
		final List<String> codes = new ArrayList<String>();
		for (final Activity activity : activities) {
			codes.add(activity.getCode());
		}
		return codes;
	}
	
	@Transactional(readOnly = true)
	private final IdentityDetail parseIdentityDetail(final Long companyId) {
		final IdentityDetail identityDetail = new IdentityDetail();
		final Object[] result = companyIdentityRepository.readIdentityDetail(companyId);
		final Company company = (Company) result[0];
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		identityDetail.setCreatedDate(DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print((DateTime) result[2]));
		identityDetail.setRequestedDate(DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print((DateTime) result[1]));
		identityDetail.setDenomination(company.getDenomination());
		identityDetail.setTradename(company.getTradename());
		identityDetail.setBuildDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(company.getBuildDate()));
		identityDetail.setUrl((String) result[3]);
		identityDetail.setFileUrl(ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.identity);
		identityDetail.setActivities(readCodes(activities));
		return identityDetail;
	}
	
	@Override
	@Transactional(readOnly = true)
	public IdentityDetail readIdentityDetail(final Long companyId) {
		final IdentityDetail identityDetail = parseIdentityDetail(companyId);
		final List<Object[]> results = identityHistoryRepository.findAllIdentityHistory(companyId);
		for (final Object[] result : results) {
			final IdentityHistory history = (IdentityHistory) result[0];
			identityDetail.getValidatesBy().add((String) result[1]);
			identityDetail.getResponses().add(history.getResponse());
			identityDetail.getHistoriesDate().add(DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(history.getHistoryDate()));
			identityDetail.getMessages().add(history.getMessage());
		}
		return identityDetail;
	}
	
	@Transactional
	private final IdentityHistory postIdentityHistory(final Long adminId, final IdentityResponseForm identityResponseForm) {
		final IdentityHistory identityHistory = new IdentityHistory();
		identityHistory.setCompanyId(identityResponseForm.getId());
		identityHistory.setValidateBy(adminId);
		identityHistory.setResponse(identityResponseForm.isResponse());
		identityHistory.setHistoryDate(new DateTime(Date.from(Instant.now())));
		identityHistory.setMessage(identityResponseForm.getMessage());
		return identityHistoryRepository.save(identityHistory);
	}
	
	@Override
	@Transactional
	public Object[] validateIdentity(final Long adminId, final IdentityResponseForm identityResponseForm) {
		final Object[] result = new Object[2];
		final Long companyId = identityResponseForm.getId();
		result[0] = companyIdentityRepository.findUserIdByCompanyId(companyId).get();
		if(!identityResponseForm.isResponse()) {
			companyIdentityRepository.updateCosultedByCompanyId(companyId);
		} else {
			companyRepository.updateEnabledById(true, companyId);
			companyIdentityRepository.deleteById(companyId);
		}
		postIdentityHistory(adminId, identityResponseForm);
		avatarService.deleteAvatar(companyId, AvatarType.identity);
		result[1] = companyRepository.findTradenameById(companyId).get();
		return result;
	}
	
	@Override
	@Transactional
	public String deleteIdentity(final Long companyId) throws NotFoundException {
		final Optional<CompanyIdentity> uOptional = companyIdentityRepository.findById(companyId);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final CompanyIdentity companyIdentity = uOptional.get();
		companyIdentityRepository.delete(companyIdentity);
		avatarService.deleteAvatar(companyId, AvatarType.identity);
		return companyRepository.findTradenameById(companyId).get();
	}
	
	@Override
	@Transactional
	public void deleteIdentities(final List<Long> lines) {
		companyIdentityRepository.deleteIdentities(lines);
		avatarService.deleteAllAvatar(lines, AvatarType.identity);
	}
	
}
