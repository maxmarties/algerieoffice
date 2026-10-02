package com.rinitec.algerieoffice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumFormuleRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.admins.CurrSocialFooter;
import com.rinitec.algerieoffice.web.modal.company.CurrentCommunication;
import com.rinitec.algerieoffice.web.modal.company.CurrentProspect;

@Service
public class AttributeService implements IAttributeService {

	private CompanyRepository companyRepository;
	private PremiumFormuleRepository premiumFormuleRepository;
	
	@Autowired
	public AttributeService(CompanyRepository companyRepository, PremiumFormuleRepository premiumFormuleRepository) {
		this.companyRepository = companyRepository;
		this.premiumFormuleRepository = premiumFormuleRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean checkCompanyPublished(final Long companyId) {
		try {
			final Long publishedId = companyRepository.checkCompanyPublished(companyId);
			return publishedId != null;
		} catch (Exception e) {}
		return false;
	}
	
	@Override
	public CurrentConfig attributeNotfound(final Model model, final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		model.addAttribute("currentConfig", currentConfig);
		return currentConfig;
	}
	
	@Transactional(readOnly = true)
	private final CurrSocialFooter readCurrSocialFooter() {
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.SOCIALFOOTER_FORMULE);
		return new CurrSocialFooter(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentUser attributeAuthentified(final Model model, final Authentication authentication) {
		model.addAttribute("currentSocial", readCurrSocialFooter());
		if (authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			final CurrentUser currentUser = new CurrentUser(((LocalUser) authentication.getPrincipal()).getUser());
			model.addAttribute("currentUser", currentUser);
			if(!currentUser.admin() && currentUser.hasCompany()) {
				model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
			}
			return currentUser;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentConfig attributeConfig(final Model model, final String config, final LocalUser localUser) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final CurrentUser currentUser = new CurrentUser(localUser.getUser());
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("currentSocial", readCurrSocialFooter());
		if(!currentUser.admin() && currentUser.hasCompany()) {
			model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
		}
		return currentConfig;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentUser attributeAuthentified(final Model model, final Authentication authentication, final String config) {
		model.addAttribute("currentSocial", readCurrSocialFooter());
		model.addAttribute("currentConfig", new CurrentConfig(config));
		if (authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			final CurrentUser currentUser = new CurrentUser(((LocalUser) authentication.getPrincipal()).getUser());
			model.addAttribute("currentUser", currentUser);
			if(!currentUser.admin() && currentUser.hasCompany()) {
				model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
			}
			return currentUser;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentUser attributePreview(final Model model, final LocalUser localUser, final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config, true);
		final CurrentUser currentUser = new CurrentUser(localUser.getUser());
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("currentSocial", readCurrSocialFooter());
		if(!currentUser.admin() && currentUser.hasCompany()) {
			model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
		}
		return currentUser;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentUser attributeExplorer(final Model model, final Authentication authentication, final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config, true);
		model.addAttribute("currentSocial", readCurrSocialFooter());
		model.addAttribute("currentConfig", currentConfig);
		if (authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			final CurrentUser currentUser = new CurrentUser(((LocalUser) authentication.getPrincipal()).getUser());
			model.addAttribute("currentUser", currentUser);
			if(!currentUser.admin() && currentUser.hasCompany()) {
				model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
			}
			return currentUser;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentUser attributeUser(final Model model, final LocalUser localUser) {
		final CurrentUser currentUser = new CurrentUser(localUser.getUser());
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("currentSocial", readCurrSocialFooter());
		if(!currentUser.admin() && currentUser.hasCompany()) {
			model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
		}
		return currentUser;
	}
	
	@Override
	public CurrentUser attributeUser(final Model model, final String config, final LocalUser localUser) {
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return attributeUser(model, localUser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentCompany attributeCompany(final Model model, final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final CurrentCompany currentCompany = companyRepository.getCurrentCompany(companyId);
		final CurrentCommunication currentCommunication = companyRepository.readCurrentCommunication(companyId);
		final CurrentProspect currentProspect = companyRepository.readCurrentProspect(companyId);
		model.addAttribute("currentCompany", currentCompany);
		model.addAttribute("currentCommunication", currentCommunication);
		model.addAttribute("currentProspect", currentProspect);
		model.addAttribute("currentUser", new CurrentUser(localUser.getUser()));
		model.addAttribute("currentSocial", readCurrSocialFooter());
		return currentCompany;
	}
	
	@Override
	public CurrentCompany attributeCompany(final Model model, final String config, final LocalUser localUser) {
		model.addAttribute("currentConfig", new CurrentConfig(config));
		return attributeCompany(model, localUser);
	}
	
	@Override
	public CurrSocialFooter attributeCurrSocial(final Model model, final Authentication authentication, final String config) {
		final CurrSocialFooter currSocial = readCurrSocialFooter();
		model.addAttribute("currentSocial", currSocial);
		model.addAttribute("currentConfig", new CurrentConfig(config));
		if (authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			final CurrentUser currentUser = new CurrentUser(((LocalUser) authentication.getPrincipal()).getUser());
			model.addAttribute("currentUser", currentUser);
			if(!currentUser.admin() && currentUser.hasCompany()) {
				model.addAttribute("currentCompany", companyRepository.getCurrentCompany(currentUser.getCompanyId()));
			}
		}
		return currSocial;
	}
	
}
