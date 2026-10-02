package com.rinitec.algerieoffice.services.admins;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.data.ActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAddressRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyBriefcaseRepository;
import com.rinitec.algerieoffice.persistence.dao.users.PrivilegeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.RoleRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.users.Privilege;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.CompanyTemp;

@Service
public class SetupDataService implements ISetupDataService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
	private AccountRepository accountRepository;
	private ActivityRepository activityRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyAccountRepository companyAccountRepository;
	private CompanyAddressRepository companyAddressRepository;
	private CompanySearchRepository companySearchRepository;
	private CompanyBriefcaseRepository companyBriefcaseRepository;
	
	@Autowired
	public SetupDataService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository,
			AccountRepository accountRepository, ActivityRepository activityRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, 
			CompanyAccountRepository companyAccountRepository, CompanyAddressRepository companyAddressRepository, CompanySearchRepository companySearchRepository, 
			CompanyBriefcaseRepository companyBriefcaseRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.privilegeRepository = privilegeRepository;
		this.accountRepository = accountRepository;
		this.activityRepository = activityRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.companyAddressRepository = companyAddressRepository;
		this.companySearchRepository = companySearchRepository;
		this.companyBriefcaseRepository = companyBriefcaseRepository;
	}
	
	@Transactional
	private final Privilege createPrivilegeIfNotFound(final String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
		}
		return privilege;
	}
	
	@Transactional
	private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
            role = new Role(name);
        }
		role.setPrivileges(privileges);
        return roleRepository.save(role);
	}
	
	@Transactional
	private final User createUserIfNotFound(final String firstName, final String lastName, 
			final String email, final String password, final Collection<Role> roles) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setAdmin(true);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(password));
			user.setEnabled(true);
		}
		user.setRoles(roles);
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createAccountIfNotFound(final User user) {
		if(accountRepository.existsById(user.getId())) {
			return accountRepository.getOne(user.getId());
		}
		final Account account = new Account();
		account.setUserId(user.getId());
		account.setPseudo("reda");
		account.setCreateDate(new DateTime(Date.from(Instant.now())));
		account.setIp("0:0:0:0:1");
		return accountRepository.save(account);
	}
	
	@Transactional
	private final void createActivities() {
		try {
			final List<Activity> activities = new ArrayList<Activity>();
			final OPCPackage opcPackage = OPCPackage.open(new ClassPathResource("data/CodeActivities.xlsx").getFile(), PackageAccess.READ);
			final Workbook workbook = new XSSFWorkbook(opcPackage);
			final Sheet firstSheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = firstSheet.iterator();
			rowIterator.next(); // skip the header row
			while (rowIterator.hasNext()) {
				final Activity activity =  new Activity();
				final Row nextRow = rowIterator.next();
				final Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					final Cell nextCell = cellIterator.next();
					switch (nextCell.getColumnIndex()) {
					case 0: activity.setCode(String.valueOf((int) nextCell.getNumericCellValue())); break;
					case 1: activity.setSector((int) nextCell.getNumericCellValue()); break;
					case 2: activity.setUrl(nextCell.getStringCellValue());
					}
				}
				if(!activityRepository.existsByCode(activity.getCode())) {
					activities.add(activity);
				}
			}
			if(!activities.isEmpty()) {
				activityRepository.saveAll(activities);
			}
			opcPackage.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading file");
		}
	}
	
	@Transactional
	private final Company createCompanyEmptyIfNotFound() {
		if(companyRepository.existsById(1L)) {
			return companyRepository.getOne(1L);
		}
		final Company company = new Company();
		final Activity activity = activityRepository.findByCode("607096");
		company.setId(1L);
		company.setDenomination("empty_denomination");
		company.setTradename("empty_tradename");
		company.setLang("fr");
		company.setPhone("0000000000");
		company.setCompanymail("empty@algerieoffice.com");
		company.setHasAvatar(false);
		company.setActivities(new ArrayList<Activity>(Arrays.asList(activity)));
		return companyRepository.save(company);
	}
	
	@Transactional
	private final CompanyAddress createCompanyAddressEmptyIfNotFound(final Company company) {
		if(companyAddressRepository.existsById(company.getId())) {
			return companyAddressRepository.getOne(company.getId());
		}
		final CompanyAddress companyAddress = new CompanyAddress();
		companyAddress.setAddress("empty_address");
		companyAddress.setPostal("00000");
		companyAddress.setWilaya(0);
		companyAddress.setCompany(company);
		return companyAddressRepository.save(companyAddress);
	}
	
	@Transactional
	private final CompanySeo createCompanySeoEmptyIfNotFound(final Company company) {
		if(companySeoRepository.existsById(company.getId())) {
			return companySeoRepository.getOne(company.getId());
		}
		final CompanySeo companySeo = new CompanySeo();
		companySeo.setDescription("empty_description");
		companySeo.setCompany(company);
		return companySeoRepository.save(companySeo);
	}
	
	@Transactional
	private final CompanyAccount createCompanyAccountEmptyIfNotFound(final Long companyId) {
		if(companyAccountRepository.existsById(companyId)) {
			return companyAccountRepository.getOne(companyId);
		}
		final CompanyAccount companyAccount = new CompanyAccount();
		companyAccount.setCompanyId(companyId);
		companyAccount.setCreatedById(0L);
		companyAccount.setCreatedDate(new DateTime(Date.from(Instant.now())));
		companyAccount.setModifiedDate(new DateTime(Date.from(Instant.now())));
		return companyAccountRepository.save(companyAccount);
	}
	
	@Transactional
	private final CompanySearch createCompanySearchEmptyIfNotFound(final Long companyId) {
		if(companySearchRepository.existsById(companyId)) {
			return companySearchRepository.getOne(companyId);
		}
		final CompanySearch companySearch = new CompanySearch(companyId);
		return companySearchRepository.save(companySearch);
	}
	
	@Transactional
	private final void createCompanyEmpty() {
		final Company emptyCompany = createCompanyEmptyIfNotFound();
		createCompanyAddressEmptyIfNotFound(emptyCompany);
		createCompanySeoEmptyIfNotFound(emptyCompany);
		createCompanyAccountEmptyIfNotFound(emptyCompany.getId());
		createCompanySearchEmptyIfNotFound(emptyCompany.getId());
	}

	@Override
	@Transactional
	public void initData() {
		final Privilege passwordPrivilege = createPrivilegeIfNotFound("PASSWORD_PRIVILEGE");
		final Privilege accountPrivilege = createPrivilegeIfNotFound("ACCOUNT_PRIVILEGE");
		final Privilege visitorPrivilege = createPrivilegeIfNotFound("VISITOR_PRIVILEGE");
		final Privilege companyVisitPrivilege = createPrivilegeIfNotFound("COMPANY_VISIT_PRIVILEGE");
		final Privilege companyAutorPrivilege = createPrivilegeIfNotFound("COMPANY_AUTOR_PRIVILEGE");
		final Privilege companyEditPrivilege = createPrivilegeIfNotFound("COMPANY_EDIT_PRIVILEGE");
		final Privilege companyManagerPrivilege = createPrivilegeIfNotFound("COMPANY_MANAGER_PRIVILEGE");
		final Privilege companyAdminPrivilege = createPrivilegeIfNotFound("COMPANY_ADMIN_PRIVILEGE");
		final Privilege supportAutorPrivilege = createPrivilegeIfNotFound("SUPPORT_AUTOR_PRIVILEGE");
		final Privilege supportManagerPrivilege = createPrivilegeIfNotFound("SUPPORT_MANAGER_PRIVILEGE");
		final Privilege supportAdminPrivilege = createPrivilegeIfNotFound("SUPPORT_ADMIN_PRIVILEGE");
		final Role roleAccount = createRoleIfNotFound("ROLE_ACCOUNT", new ArrayList<>(Arrays.asList(accountPrivilege, passwordPrivilege)));
		createRoleIfNotFound("ROLE_VISITOR", new ArrayList<>(Arrays.asList(visitorPrivilege)));
		createRoleIfNotFound("ROLE_COMPANY_VISIT", new ArrayList<>(Arrays.asList(companyVisitPrivilege)));
		createRoleIfNotFound("ROLE_COMPANY_AUTOR", new ArrayList<>(Arrays.asList(companyVisitPrivilege, companyAutorPrivilege)));
		createRoleIfNotFound("ROLE_COMPANY_EDIT", new ArrayList<>(Arrays.asList(companyVisitPrivilege, companyAutorPrivilege, 
				companyEditPrivilege)));
		createRoleIfNotFound("ROLE_COMPANY_MANAGER", new ArrayList<>(Arrays.asList(companyVisitPrivilege, companyAutorPrivilege, 
				companyEditPrivilege, companyManagerPrivilege)));
		createRoleIfNotFound("ROLE_COMPANY_ADMIN", new ArrayList<>(Arrays.asList(companyVisitPrivilege, companyAutorPrivilege, 
				companyEditPrivilege, companyManagerPrivilege, companyAdminPrivilege)));
		createRoleIfNotFound("ROLE_SUPPORT_AUTOR", new ArrayList<>(Arrays.asList(supportAutorPrivilege)));
		createRoleIfNotFound("ROLE_SUPPORT_MANAGER", new ArrayList<>(Arrays.asList(supportAutorPrivilege, supportManagerPrivilege)));
		final Role roleAdmin = createRoleIfNotFound("ROLE_SUPPORT_ADMIN", new ArrayList<>(Arrays.asList(supportAutorPrivilege, 
				supportManagerPrivilege, supportAdminPrivilege)));
		final User reda = createUserIfNotFound("Mohamed Reda", "Bensaad", ConstraintesURL.EMAIL_ADMINISTRATOR, "Rplus+ads@87!", 
				new ArrayList<Role>(Arrays.asList(roleAccount, roleAdmin)));
		createAccountIfNotFound(reda);
		createActivities();
		createCompanyEmpty();
	}
	
	@Transactional
	private final String generatePseudo(final String username) {
		String pseudo = ParseUtil.generatePseudo(10, username);
		while(accountRepository.existsByPseudo(pseudo)) {
			pseudo = ParseUtil.generatePseudo(10, username);
		}
		return pseudo;
	}
	
	@Transactional
	private final User createUser(final Long companyId, final CompanyTemp companyTemp) {
		final User user = new User();
		user.setCompanyId(companyId);
		user.setFirstName(companyTemp.getFirstname());
		user.setLastName(companyTemp.getLastname());
		user.setEmail(companyTemp.getEmail());
		user.setPassword(passwordEncoder.encode(companyTemp.getPassword()));
		user.setRoles(new ArrayList<Role>(Arrays.asList(roleRepository.findByName("ROLE_ACCOUNT"), 
				roleRepository.findByName("ROLE_COMPANY_ADMIN"))));
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	@Transactional
	private final Account createAccount(final User user) {
		final Account account = new Account();
		account.setUserId(user.getId());
		account.setPseudo(generatePseudo(user.getDisplayName()));
		account.setCreateDate(new DateTime(Date.from(Instant.now())));
		account.setIp("0:0:0:0:100");
		account.setHasAccepte(false);
		account.setActivateDate(new DateTime(Date.from(Instant.now())));
		return accountRepository.save(account);
	}
	
	@Transactional
	private final Company createCompany(final CompanyTemp companyTemp) {
		final Company company = new Company();
		final Activity activity = activityRepository.findByCode(companyTemp.getActivity());
		company.setDenomination(companyTemp.getTradename());
		company.setTradename(companyTemp.getTradename());
		company.setLang(companyTemp.getLang());
		company.setPhone(companyTemp.getPhone());
		company.setCompanymail(companyTemp.getEmail());
		company.setHasAvatar(false);
		company.setEnabled(true);
		company.setBuildDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(companyTemp.getDate()));
		company.setActivities(new ArrayList<Activity>(Arrays.asList(activity)));
		return companyRepository.save(company);
	}
	
	@Transactional
	private final CompanyAddress createCompanyAddress(final Company company, final CompanyTemp companyTemp) {
		final CompanyAddress companyAddress = new CompanyAddress();
		companyAddress.setAddress(companyTemp.getAddress());
		companyAddress.setPostal(companyTemp.getPostal());
		companyAddress.setWilaya(companyTemp.getWilaya());
		companyAddress.setCompany(company);
		return companyAddressRepository.save(companyAddress);
	}
	
	@Transactional
	private final CompanySeo createCompanySeo(final Company company, final CompanyTemp companyTemp) {
		final CompanySeo companySeo = new CompanySeo();
		companySeo.setDescription(companyTemp.getDescription());
		companySeo.setUrl(companyTemp.getUrl());
		companySeo.setCompany(company);
		return companySeoRepository.save(companySeo);
	}
	
	@Transactional
	private final CompanyAccount createCompanyAccount(final Long companyId, final Long userId) {
		final CompanyAccount companyAccount = new CompanyAccount();
		companyAccount.setCompanyId(companyId);
		companyAccount.setCreatedById(userId);
		companyAccount.setCreatedDate(new DateTime(Date.from(Instant.now())));
		companyAccount.setModifiedDate(new DateTime(Date.from(Instant.now())));
		return companyAccountRepository.save(companyAccount);
	}
	
	@Transactional
	private final CompanySearch createCompanySearch(final Long companyId) {
		final CompanySearch companySearch = new CompanySearch(companyId);
		return companySearchRepository.save(companySearch);
	}
	
	@Transactional
	private final CompanyBriefcase createCompanyBriefcase(final Long companyId, final CompanyTemp companyTemp) {
		final CompanyBriefcase companyBriefcase = new CompanyBriefcase(companyId);
		companyBriefcase.setBriefcase(2);
		companyBriefcase.setWarehouse(false);
		companyBriefcase.setCapital(companyTemp.getCapital());
		companyBriefcase.setType(companyTemp.getType());
		return companyBriefcaseRepository.save(companyBriefcase);
	}
	
	@Transactional
	private final void postCompanyTemp(final CompanyTemp companyTemp) {
		final Company company = createCompany(companyTemp);
		final User user = createUser(company.getId(), companyTemp);
		createCompanyAddress(company, companyTemp);
		createCompanySeo(company, companyTemp);
		createCompanyAccount(company.getId(), user.getId());
		createCompanySearch(company.getId());
		createCompanyBriefcase(company.getId(), companyTemp);
		createAccount(user);
	}
	
	@Override
	@Transactional
	public void initTemp() {
		try {
			final List<CompanyTemp> companiesTemp = new ArrayList<CompanyTemp>();
			final OPCPackage opcPackage = OPCPackage.open(new ClassPathResource("data/TempCompanies.xlsx").getFile(), PackageAccess.READ);
			final Workbook workbook = new XSSFWorkbook(opcPackage);
			final Sheet firstSheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = firstSheet.iterator();
			rowIterator.next(); // skip the header row
			while (rowIterator.hasNext()) {
				final CompanyTemp companyTemp = new CompanyTemp();
				final Row nextRow = rowIterator.next();
				final Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					final Cell nextCell = cellIterator.next();
					switch (nextCell.getColumnIndex()) {
					case 0: companyTemp.setFirstname(nextCell.getStringCellValue()); break;
					case 1: companyTemp.setLastname(nextCell.getStringCellValue()); break;
					case 2: companyTemp.setEmail(nextCell.getStringCellValue()); break;
					case 3: companyTemp.setPassword(nextCell.getStringCellValue()); break;
					case 4: companyTemp.setTradename(nextCell.getStringCellValue()); break;
					case 5: companyTemp.setActivity(String.valueOf((int) nextCell.getNumericCellValue())); break;
					case 6: companyTemp.setDescription(nextCell.getStringCellValue()); break;
					case 7: companyTemp.setLang(nextCell.getStringCellValue()); break;
					case 8: companyTemp.setAddress(nextCell.getStringCellValue()); break;
					case 9: companyTemp.setPostal(String.valueOf((int) nextCell.getNumericCellValue())); break;
					case 10: companyTemp.setWilaya((int) nextCell.getNumericCellValue()); break;
					case 11: companyTemp.setPhone(nextCell.getStringCellValue()); break;
					case 12: companyTemp.setUrl(nextCell.getStringCellValue()); break;
					case 13: companyTemp.setDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(nextCell.getDateCellValue().getTime())); break;
					case 14: companyTemp.setCapital((int) nextCell.getNumericCellValue()); break;
					case 15: companyTemp.setType((int) nextCell.getNumericCellValue()); break;
					}
				}
				companiesTemp.add(companyTemp);
			}
			opcPackage.close();
			workbook.close();
			for (final CompanyTemp companyTemp : companiesTemp) {
				postCompanyTemp(companyTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading file temp");
		}
	}
	
}
