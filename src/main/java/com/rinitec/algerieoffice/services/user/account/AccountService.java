package com.rinitec.algerieoffice.services.user.account;

import java.time.Instant;
import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinitec.algerieoffice.persistence.dao.journal.JournalAdminRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalAdmin;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;

import ua_parser.Client;
import ua_parser.Parser;

@Service
public class AccountService implements IAccountService {

	private AccountRepository accountRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private JournalAdminRepository journalAdminRepository;
	
	@Autowired
	public AccountService(AccountRepository accountRepository, JournalCompanyRepository journalCompanyRepository, JournalAdminRepository journalAdminRepository) {
		this.accountRepository = accountRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.journalAdminRepository = journalAdminRepository;
	}
	
	@Override
	@Transactional
	public Account activateAccount(final Long userId) {
		final Account account = accountRepository.findById(userId).get();
		if(account.getActivateDate() == null) {
			account.setNumberOfVisits(account.getNumberOfVisits() + 1);
			account.setActivateDate(new DateTime(Date.from(Instant.now())));
			account.setLastLoginDate(new DateTime(Date.from(Instant.now())));
			return accountRepository.save(account);
		}
		return account;
	}
	
	@Transactional
	private final Account updateAccountVisit(final Long userId) {
		final Account account = accountRepository.findById(userId).get();
		account.setNumberOfVisits(account.getNumberOfVisits() + 1);
		account.setLastLoginDate(new DateTime(Date.from(Instant.now())));
		return accountRepository.save(account);
	}
	
	private final String parseDevice(final String userAgent) {
		try {
			final Parser parser = new Parser();
			final Client client = parser.parse(userAgent);
			return client.userAgent.family.concat(" ").concat(client.userAgent.major).concat(" - ").concat(client.os.family).concat(" ").concat(client.os.major)
					.concat(" (").concat(client.device.family.equals("Other") ? "PC" : client.device.family).concat(")");
		} catch(Exception e) {e.printStackTrace();}
		return null;
	}
	
	@Transactional
	private final JournalCompany updateCompanyVisit(final Long userId, final Long companyId, final String device) {
		final JournalCompany journalCompany = new JournalCompany();
		journalCompany.setCompanyId(companyId);
		journalCompany.setUserId(userId);
		journalCompany.setAction(ConstraintesJournal.COMPANY_LOGIN_USER);
		journalCompany.setElement(device.length() > 250 ? device.substring(0, 246).concat("..") : device);
		journalCompany.setPostedDate(new DateTime(Date.from(Instant.now())));
		return journalCompanyRepository.save(journalCompany);
	}
	
	private final JournalAdmin updateAdminVisit(final Long userId, final String device) {
		final JournalAdmin journalAdmin = new JournalAdmin();
		journalAdmin.setUserId(userId);
		journalAdmin.setAction(ConstraintesJournal.ADMIN_LOGIN_USER);
		journalAdmin.setElement(device.length() > 250 ? device.substring(0, 246).concat("..") : device);
		journalAdmin.setPostedDate(new DateTime(Date.from(Instant.now())));
		return journalAdminRepository.save(journalAdmin);
	}
	
	@Override
	@Transactional
	public Account registerVisit(final User user, final String userAgent) {
		final Account account = updateAccountVisit(user.getId());
		if(user.getAdmin()) {
			updateAdminVisit(user.getId(), parseDevice(userAgent));
		} else if(user.getCompanyId() != null) {
			updateCompanyVisit(user.getId(), user.getCompanyId(), parseDevice(userAgent));
		}
		return account;
	}
	
}
