package com.rinitec.algerieoffice.services.inbox;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.inbox.SupportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.modal.medias.Screenshot;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.services.medias.IScreenshotService;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.inbox.SupportInfo;
import com.rinitec.algerieoffice.web.modal.inbox.SupportNotification;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;
import com.rinitec.algerieoffice.web.modal.inbox.SupportSheet;

@Service
public class SupportService implements ISupportService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private SupportRepository supportRepository;
	private IScreenshotService screenshotService;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public SupportService(UserRepository userRepository, CompanyRepository companyRepository, 
			SupportRepository supportRepository, IScreenshotService screenshotService, 
			ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.supportRepository = supportRepository;
		this.screenshotService = screenshotService;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional
	public void deleteAllExpiredSince(final DateTime now) {
		final List<UUID> lines = supportRepository.findAllScreenUUIDExpiredSince(now);
		supportRepository.deleteAllExpiredSince(now);
		screenshotService.deleteScreenshots(lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countSupportOnline() {
		int moderators = 1;
		final List<String> emails = userRepository.findAllEmailAdmin();
		for (final String email : emails) {
			if(activeUserStore.hasLogged(email)) {
				moderators++;
			}
		}
		return moderators;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countSupportUser(final Long userId) {
		final long count = supportRepository.countNewSupportUser(userId);
		return count >= 100L ? 99 : (int) count;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SupportSheet> findAllSupportSheetUser(final Long userId, final int page, final int rows) {
		return supportRepository.findAllSupportSheetUser(userId, page, rows);
	}
	
	@Override
	@Transactional
	public void updateConsulted(final Long userId, final String supportId) {
		try {
			supportRepository.updateConsultedByUserId(UUID.fromString(supportId), userId);
		} catch (IllegalArgumentException e) {e.printStackTrace();}
	}
	
	@Override
	@Transactional
	public void updateAllConsulted(final Long userId) {
		supportRepository.updateAllConsultedByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findAllEmailsAdmin() {
		return userRepository.findAllEmailAdmin();
	}
	
	@Override
	@Transactional
	public Support addSupport(final SupportPush supportPush) {
		final Support support = new Support();
		support.setUserId(supportPush.getUserId());
		support.setAdminId(supportPush.getAdminId());
		support.setMessage(supportPush.parseMessage());
		support.setPostedDate(new DateTime(Date.from(Instant.now())));
		return supportRepository.save(support);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasAllConsulted() {
		return supportRepository.countNewSupportAdmin() == 0L;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countSupportAdmin() {
		final long count = supportRepository.countNewSupportAdmin();
		return count >= 100L ? 99 : (int) count;
	}
	
	@Override
	@Transactional
	public void updateConsultedAdmin(final String supportId) {
		try {
			supportRepository.updateConsultedByAdminId(UUID.fromString(supportId));
		} catch (IllegalArgumentException e) {e.printStackTrace();}
	}
	
	@Override
	@Transactional
	public void updateAllConsultedAdmin() {
		supportRepository.updateAllConsulted();
	}
	
	@Override
	@Transactional
	public void updateAllConsultedAdmin(final Long userId) {
		supportRepository.updateAllConsultedByAdminId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SupportNotification> findAllSupportNotification(final int page, final int rows) {
		return supportRepository.findAllSupportNotification(page, rows);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SupportInfo readSupportInfo(final Long userId) {
		final Object[] companyInfo = companyRepository.findCompanyInfoFromUserId(userId);
		final boolean hasLogin = activeUserStore.hasLogged((String) companyInfo[0]);
		return new SupportInfo((String) companyInfo[1], (String) companyInfo[2], (Integer) companyInfo[3], hasLogin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SupportSheet> findAllSupportSheetAdmin(final Long userId, final int page, final int rows) {
		return supportRepository.findAllSupportSheetAdmin(userId, page, rows);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findUserEmail(final Long userId) {
		final Optional<String> uOptional = userRepository.findEmailById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findAllEmailsAdminOne(final Long adminId) {
		return userRepository.findAllEmailAdminOne(adminId);
	}
	
	@Transactional
	private final Support postSupportUserFile(final Long userId, final UUID screenUUID) {
		final Support support = new Support();
		support.setUserId(userId);
		support.setScreenUUID(screenUUID);
		support.setPostedDate(new DateTime(Date.from(Instant.now())));
		return supportRepository.save(support);
	}
	
	private final SupportPush parseSupportPushFile(final User user, final Support support) {
		final SupportPush supportPush = new SupportPush();
		supportPush.setId(support.getId().toString());
		supportPush.setUserId(support.getUserId());
		supportPush.setAdminId(support.getAdminId());
		supportPush.setUsername(user.getDisplayName());
		supportPush.setUserAvatar(user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=42&height=42"
				: "/static/picts/avatars/account_mini-min.jpg");
		supportPush.setMessage(ConstraintesURL.URL_SCREENSHOT + "?id=" + support.getScreenUUID().toString());
		supportPush.setTime(DateTimeFormat.forPattern("HH:mm").print(support.getPostedDate()));
		supportPush.setScreenshot(true);
		return supportPush;
	}
	
	@Override
	@Transactional
	public SupportPush addSupportUserFile(final User user, final MultipartFile file) {
		final Screenshot screenshot = screenshotService.addScreenshot(file);
		final Support support = postSupportUserFile(user.getId(), screenshot.getId());
		return parseSupportPushFile(user, support);
	}
	
	@Transactional
	private final Support postSupportAdminFile(final Long userId, final Long adminId, final UUID screenUUID) {
		final Support support = new Support();
		support.setUserId(userId);
		support.setAdminId(adminId);
		support.setScreenUUID(screenUUID);
		support.setPostedDate(new DateTime(Date.from(Instant.now())));
		return supportRepository.save(support);
	}
	
	@Override
	@Transactional
	public SupportPush addSupportAdminFile(final Long userId, final User admin, final MultipartFile file) {
		final Screenshot screenshot = screenshotService.addScreenshot(file);
		final Support support = postSupportAdminFile(userId, admin.getId(), screenshot.getId());
		return parseSupportPushFile(admin, support);
	}
	
	@Transactional
	private final Support postSupportHelp(final Long adminId, final Long userId, final String message) {
		final Support support = new Support();
		support.setUserId(userId);
		support.setAdminId(adminId);
		support.setMessage(message);
		support.setPostedDate(new DateTime(Date.from(Instant.now())));
		return supportRepository.save(support);
	}
	
	private final SupportPush parseSupportPushHelp(final String adminname, final Support support) {
		final SupportPush supportPush = new SupportPush();
		supportPush.setId(support.getId().toString());
		supportPush.setUserId(support.getUserId());
		supportPush.setAdminId(support.getAdminId());
		supportPush.setUsername(adminname);
		supportPush.setMessage(support.getMessage());
		supportPush.setTime(DateTimeFormat.forPattern("HH:mm").print(support.getPostedDate()));
		supportPush.setScreenshot(false);
		return supportPush;
	}
	
	@Override
	@Transactional
	public SupportPush addSupportHelp(final Long userId, final String message) {
		final UserMini adminMini = userRepository.findAdmSupportMini();
		final Support support = postSupportHelp(adminMini.getUserId(), userId, message);
		return parseSupportPushHelp(adminMini.getEmail(), support);
	}
	
}
