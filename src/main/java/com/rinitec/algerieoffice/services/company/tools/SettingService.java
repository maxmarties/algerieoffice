package com.rinitec.algerieoffice.services.company.tools;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.SettingsRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Settings;
import com.rinitec.algerieoffice.web.form.company.tools.SettingForm;

@Service
public class SettingService implements ISettingService {

	private UserRepository userRepository;
	private SettingsRepository settingsRepository;
	private CompanyAccountRepository companyAccountRepository;
	
	@Autowired
	public SettingService(UserRepository userRepository, SettingsRepository settingsRepository, 
			CompanyAccountRepository companyAccountRepository) {
		this.userRepository = userRepository;
		this.settingsRepository = settingsRepository;
		this.companyAccountRepository = companyAccountRepository;
	}
	
	@Transactional(readOnly = true)
	private final Settings readSettings(final Long companyId) {
		final Optional<Settings> uOptional = settingsRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final String readAlertname(final Long companyId) {
		final Long createdBy = companyAccountRepository.findCreatedById(companyId).get();
		return userRepository.findUsernameById(createdBy).get();
	}
	
	@Override
	@Transactional(readOnly = true)
	public SettingForm readSettingForm(final Long companyId) {
		final SettingForm settingForm = new SettingForm();
		final Settings settings = readSettings(companyId);
		if(settings != null) {
			settingForm.parseParams(settings.getParams());
			settingForm.setService(settings.isService());
			settingForm.setPosthome(settings.isPosthome());
			settingForm.setIndexed(settings.isIndexed());
			settingForm.setBanner(settings.getBanner());
		} else {
			settingForm.parseDefaultParams();
			settingForm.setService(false);
			settingForm.setPosthome(true);
			settingForm.setIndexed(true);
		}
		settingForm.setId(companyId);
		settingForm.setAlertname(readAlertname(companyId));
		return settingForm;
	}
	
	@Override
	@Transactional
	public Settings updateSettings(final SettingForm settingForm) {
		final Optional<Settings> uOptional = settingsRepository.findById(settingForm.getId());
		final Settings settings = uOptional.isPresent() ? uOptional.get() : new Settings(settingForm.getId());
		settings.setParams(settingForm.builderParams());
		settings.setService(settingForm.isService());
		settings.setPosthome(settingForm.isPosthome());
		settings.setIndexed(settingForm.isIndexed());
		settings.setBanner(settingForm.getBanner());
		return settingsRepository.save(settings);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean readService(final Long companyId) {
		final Optional<Boolean> uOptional = settingsRepository.findServiceById(companyId);
		return uOptional.isPresent() ? uOptional.get() : false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String readBanner(final Long companyId) {
		final Optional<String> uOptional = settingsRepository.findBannerById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
}
