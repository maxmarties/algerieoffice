package com.rinitec.algerieoffice.services.admins.companies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.WidgetB2CRepository;
import com.rinitec.algerieoffice.persistence.dao.company.newsletter.BudgetRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.services.admins.IDeleteCompanyService;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.companies.FeatureForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyCustomer;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyDelete;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyFeature;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyInfo;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyProfile;
import com.rinitec.algerieoffice.web.modal.company.tools.PremiumLine;

@Service
public class AdmCompanyService implements IAdmCompanyService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private PremiumRepository premiumRepository;
	private BudgetRepository budgetRepository;
	private WidgetB2CRepository widgetB2CRepository;
	private IDeleteCompanyService deleteCompanyService;
	
	@Autowired
	public AdmCompanyService(UserRepository userRepository, RoleRepository roleRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, 
			PremiumRepository premiumRepository, BudgetRepository budgetRepository, WidgetB2CRepository widgetB2CRepository, IDeleteCompanyService deleteCompanyService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.premiumRepository = premiumRepository;
		this.budgetRepository = budgetRepository;
		this.widgetB2CRepository = widgetB2CRepository;
		this.deleteCompanyService = deleteCompanyService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCompanyList(final Integer filter, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = companyRepository.countAllCompanyAdmin(filter, search);
		final List<AdmCompanyLine> lines = countResult == 0L ? new ArrayList<AdmCompanyLine>() 
				: companyRepository.findAllCompanyAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional
	private final User deactivateUserCompany(final User user) {
		user.setCompanyId(null);
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_VISITOR"))));
		return userRepository.save(user);
	}
	
	@Transactional
	private final List<String> deactivateAllUserCompany(final Long companyId) {
		final List<String> emails = new ArrayList<String>();
		final List<User> users = userRepository.findByCompanyId(companyId);
		for (final User user : users) {
			emails.add(user.getEmail());
			deactivateUserCompany(user);
		}
		return emails;
	}
	
	@Override
	@Transactional
	public AdmCompanyDelete deleteCompany(final Long companyId) {
		if(!companyRepository.existsById(companyId)) {
			throw new NotFoundException("message.error.notfound");
		}
		final List<String> emails = deactivateAllUserCompany(companyId);
		final Company company = deleteCompanyService.deleteCompany(companyId);
		return new AdmCompanyDelete(company, emails);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCompanyProfileList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = companyRepository.countAllCompanyAdmin(filter, search);
		final List<AdmCompanyProfile> lines = countResult == 0L ? new ArrayList<AdmCompanyProfile>() 
				: companyRepository.findAllCompanyProfileAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCompanyFeatureList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = companyRepository.countAllCompanyAdmin(filter, search);
		final List<AdmCompanyFeature> lines = countResult == 0L ? new ArrayList<AdmCompanyFeature>() 
				: companyRepository.findAllCompanyFeatureAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAdmCompanyCustomerList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = companyRepository.countAllCompanyB2CAdmin(filter, search);
		final List<AdmCompanyCustomer> lines = countResult == 0L ? new ArrayList<AdmCompanyCustomer>() 
				: companyRepository.findAllCompanyB2CAdmin(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public String deleteWidgetB2C(final Long id) {
		if(!widgetB2CRepository.existsById(id)) {
			throw new NotFoundException("message.error.notfound");
		}
		widgetB2CRepository.deleteById(id);
		return companyRepository.findTradenameById(id).get();
	}
	
	@Override
	@Transactional
	public void deleteWidgetsB2C(final List<Long> lines) {
		if(lines.isEmpty() || lines.size() != (int) widgetB2CRepository.countWidgets(lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		widgetB2CRepository.deleteWidgets(lines);
	}
	
	@Transactional(readOnly = true)
	private final String readCompanyUrl(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmCompanyInfo readAdmCompanyInfo(final Long companyId) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Company company = uOptional.get();
			return new AdmCompanyInfo(company, readCompanyUrl(companyId));
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PremiumLine> findAllPremiumCompanyList(final Long companyId) {
		return premiumRepository.findAllPremium(companyId);
	}
	
	@Transactional
	private final Premium addPremium(final FeatureForm featureForm) {
		final Premium premium = new Premium();
		premium.setCompanyId(featureForm.getCompanyId());
		premium.setPass(featureForm.getPass());
		premium.setCreateDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(featureForm.getCreateDate()));
		premium.setExpiryDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(featureForm.getExpiryDate()));
		premium.setEnabled(true);
		return premiumRepository.save(premium);
	}
	
	@Override
	@Transactional
	public Premium validateFeature(final FeatureForm featureForm) {
		premiumRepository.disabledAllByCompanyId(featureForm.getCompanyId());
		return addPremium(featureForm);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmBudgetForm readAdmBudgetForm(final Long companyId) {
		if(companyRepository.existsById(companyId)) {
			final Optional<Budget> uOptional = budgetRepository.findById(companyId);
			return uOptional.isPresent() ? new AdmBudgetForm(uOptional.get()) : new AdmBudgetForm(companyId);
		}
		return null;
	}
	
	@Transactional
	private final Budget postOrUpdateBudget(final Budget budget, final AdmBudgetForm admBudgetForm) {
		budget.setEmails(admBudgetForm.getEmails().longValue());
		budget.setSendings(admBudgetForm.getSendings());
		return budgetRepository.save(budget);
	}
	
	@Override
	@Transactional
	public String updateBudget(final AdmBudgetForm admBudgetForm) {
		if(companyRepository.existsById(admBudgetForm.getCompanyId())) {
			final Optional<Budget> uOptional = budgetRepository.findById(admBudgetForm.getCompanyId());
			postOrUpdateBudget(uOptional.isPresent() ? uOptional.get() : new Budget(admBudgetForm.getCompanyId()), admBudgetForm);
			return companyRepository.findTradenameById(admBudgetForm.getCompanyId()).get();
		}
		return null;
	}
	
}
