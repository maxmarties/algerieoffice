package com.rinitec.algerieoffice.services.company.manage;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.AppearanceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.MaindisplayRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.PreferencesRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.StickyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.manage.WidgetB2CRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Preferences;
import com.rinitec.algerieoffice.persistence.modal.company.manage.Sticky;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.manage.AppearanceForm;
import com.rinitec.algerieoffice.web.form.company.manage.MaindisplayForm;
import com.rinitec.algerieoffice.web.form.company.manage.PreferenceForm;
import com.rinitec.algerieoffice.web.form.company.manage.StickyForm;
import com.rinitec.algerieoffice.web.form.company.manage.WidgetB2CForm;
import com.rinitec.algerieoffice.web.modal.company.manage.AppearanceView;
import com.rinitec.algerieoffice.web.modal.company.manage.WidgetView;

@Service
public class ManageService implements IManageService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private CompanyAccountRepository companyAccountRepository;
	private MaindisplayRepository maindisplayRepository;
	private StickyRepository stickyRepository;
	private WidgetB2CRepository widgetB2CRepository;
	private AppearanceRepository appearanceRepository;
	private PreferencesRepository preferencesRepository;
	private PremiumRepository premiumRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public ManageService(UserRepository userRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, CompanyAccountRepository companyAccountRepository, 
			MaindisplayRepository maindisplayRepository, StickyRepository stickyRepository, WidgetB2CRepository widgetB2CRepository, AppearanceRepository appearanceRepository, 
			PreferencesRepository preferencesRepository, PremiumRepository premiumRepository, IAvatarService avatarService) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.companyAccountRepository = companyAccountRepository;
		this.maindisplayRepository = maindisplayRepository;
		this.stickyRepository = stickyRepository;
		this.widgetB2CRepository = widgetB2CRepository;
		this.appearanceRepository = appearanceRepository;
		this.preferencesRepository = preferencesRepository;
		this.premiumRepository  = premiumRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public MaindisplayForm readMaindisplayForm(final Long companyId) {
		final Optional<Maindisplay> uOptional = maindisplayRepository.findById(companyId);
		return new MaindisplayForm(companyId, uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional
	public Maindisplay updateMaindisplay(final MaindisplayForm maindisplayForm) {
		final Optional<Maindisplay> uOptional = maindisplayRepository.findById(maindisplayForm.getId());
		final Maindisplay maindisplay = uOptional.isPresent() ? uOptional.get() : new Maindisplay(maindisplayForm.getId());
		maindisplay.setMainmenu(maindisplayForm.builderMainmenu());
		maindisplay.setSociety(maindisplayForm.builderSociety());
		maindisplay.setMainsidbar(maindisplayForm.builderMainsidbar());
		maindisplay.setMainfooter(maindisplayForm.builderMainfooter());
		maindisplay.setDisplay(maindisplayForm.builderDisplay());
		return maindisplayRepository.save(maindisplay);
	}
	
	@Override
	@Transactional(readOnly = true)
	public StickyForm readStickyForm(final Long companyId) {
		final StickyForm stickyForm = new StickyForm();
		final Optional<Sticky> uOptional = stickyRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final Sticky sticky = uOptional.get();
			stickyForm.setTitle(sticky.getTitle());
			stickyForm.setDescription(sticky.getDescription());
			stickyForm.setLabel(sticky.getLabel());
			stickyForm.setTarget(StringUtils.isEmpty(sticky.getUrlExtern()));
			stickyForm.setUrlExtern(sticky.getUrlExtern());
		} else {
			stickyForm.setTarget(true);
		}
		stickyForm.setId(companyId);
		return stickyForm;
	}
	
	@Override
	@Transactional
	public Sticky updateSticky(final StickyForm stickyForm) {
		final Optional<Sticky> uOptional = stickyRepository.findById(stickyForm.getId());
		final Sticky sticky = uOptional.isPresent() ? uOptional.get() : new Sticky(stickyForm.getId());
		if(!stickyForm.isTarget() && !StringUtils.isEmpty(stickyForm.getUrlExtern()) 
				&& !stickyForm.getUrlExtern().equalsIgnoreCase(sticky.getUrlExtern()) 
				&& stickyRepository.existsByUrlExtern(stickyForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		sticky.setTitle(stickyForm.getTitle());
		sticky.setDescription(stickyForm.getDescription());
		sticky.setLabel(stickyForm.getLabel());
		sticky.setUrlExtern(!stickyForm.isTarget() && !StringUtils.isEmpty(stickyForm.getUrlExtern()) ? stickyForm.getUrlExtern() : null);
		return stickyRepository.save(sticky);
	}
	
	@Override
	@Transactional(readOnly = true)
	public WidgetView readWidgetView(final Long companyId) {
		final Optional<Company> uOptional = companyRepository.findById(companyId);
		return uOptional.isPresent() ? new WidgetView(uOptional.get()) : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public WidgetB2CForm readWidgetB2CForm(final Long companyId) {
		final WidgetB2CForm widgetB2CForm = new WidgetB2CForm();
		final Optional<WidgetB2C> uOptional = widgetB2CRepository.findById(companyId);
		if(uOptional.isPresent()) {
			final WidgetB2C widgetB2C = uOptional.get();
			widgetB2CForm.setCategory(widgetB2C.getCategory());
			widgetB2CForm.setActivity(widgetB2C.getActivity());
			widgetB2CForm.setHasAvatar(widgetB2C.getHasCover());
			widgetB2CForm.setUrlAvatar(widgetB2C.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.widget 
					: "/static/picts/avatars/widget-min.jpg");
			widgetB2CForm.setUrlCover(widgetB2C.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.widget 
					: "/static/vectors/widget/m_widget".concat(String.valueOf(widgetB2C.getCategory())).concat("-min.jpg"));
			widgetB2CForm.setEnabled(widgetB2C.isEnabled());
			widgetB2CForm.setFiltred(widgetB2C.isFiltred());
		} else {
			widgetB2CForm.setHasAvatar(false);
			widgetB2CForm.setUrlAvatar("/static/picts/avatars/widget-min.jpg");
			widgetB2CForm.setUrlCover("/static/vectors/widget/m_widget9-min.jpg");
		}
		widgetB2CForm.setId(companyId);
		return widgetB2CForm;
	}
	
	@Override
	@Transactional
	public WidgetB2C upadteWidgetB2C(final WidgetB2CForm widgetB2CForm) {
		final Optional<WidgetB2C> uOptional = widgetB2CRepository.findById(widgetB2CForm.getId());
		final WidgetB2C widgetB2C = uOptional.isPresent() ? uOptional.get() : new WidgetB2C(widgetB2CForm.getId());
		widgetB2C.setCategory(widgetB2CForm.getCategory());
		widgetB2C.setActivity(widgetB2CForm.getActivity());
		widgetB2C.setEnabled(widgetB2CForm.isEnabled());
		widgetB2C.setFiltred(widgetB2CForm.isFiltred());
		widgetB2C.setHasCover(widgetB2CForm.isHasAvatar());
		if(widgetB2CForm.isHasFileChanged()) {
			if(widgetB2CForm.isHasAvatar()) {
				avatarService.postOrUpdate(widgetB2CForm.getFile(), widgetB2CForm.getId(), AvatarType.widget);
			} else {
				avatarService.deleteAvatar(widgetB2CForm.getId(), AvatarType.widget);
			}
		}
		return widgetB2CRepository.save(widgetB2C);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AppearanceView readAppearanceView(final Long companyId) {
		final Company company = companyRepository.findById(companyId).get();
		return new AppearanceView(company);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AppearanceForm readAppearanceForm(final Long companyId) {
		final Optional<Appearance> uOptional = appearanceRepository.findById(companyId);
		return new AppearanceForm(companyId, uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional
	public Appearance updateAppearance(final AppearanceForm appearanceForm, final boolean hasPremium) {
		final Optional<Appearance> uOptional = appearanceRepository.findById(appearanceForm.getId());
		final Appearance appearance = uOptional.isPresent() ? uOptional.get() : new Appearance(appearanceForm.getId());
		if(hasPremium) {
			if(appearanceForm.getStyle() == 1) {
				appearance.setTheme(appearanceForm.getTheme());
			} else {
				appearance.setTheme(17);
				appearance.setPrimaryColor(appearanceForm.getPrimaryColor());
				appearance.setSegondColor(appearanceForm.getSegondColor());
				appearance.setTreenColor(appearanceForm.getTreenColor());
				appearance.setMenuBack(appearanceForm.getMenuBack());
				appearance.setMenuColor(appearanceForm.getMenuColor());
				appearance.setMenuHover(appearanceForm.getMenuHover());
				appearance.setPopupBack(appearanceForm.getPopupBack());
				appearance.setPopupColor(appearanceForm.getPopupColor());
				appearance.setPopupHover(appearanceForm.getPopupHover());
				appearance.setSharedBack(appearanceForm.getSharedBack());
				appearance.setSharedColor(appearanceForm.getSharedColor());
				appearance.setSharedHover(appearanceForm.getSharedHover());
				appearance.setSharedFocus(appearanceForm.getSharedFocus());
				appearance.setTitleColor(appearanceForm.getTitleColor());
				appearance.setTitleAfter(appearanceForm.getTitleAfter());
				appearance.setTitleProduct(appearanceForm.getTitleProduct());
				appearance.setTitleHover(appearanceForm.getTitleHover());
				appearance.setTitleEditor(appearanceForm.getTitleEditor());
				appearance.setPageColor(appearanceForm.getPageColor());
				appearance.setTextColor(appearanceForm.getTextColor());
				appearance.setFooterColor(appearanceForm.getFooterColor());
				appearance.setButtonColor(appearanceForm.getButtonColor());
				appearance.setPrimaryTop(appearanceForm.getPrimaryTop());
				appearance.setPrimaryBottom(appearanceForm.getPrimaryBottom());
				appearance.setSegondTop(appearanceForm.getSegondTop());
				appearance.setSegondBottom(appearanceForm.getSegondBottom());
			}
		} else appearance.setTheme(1);
		return appearanceRepository.save(appearance);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllMessengers(final Long companyId) {
		return userRepository.findAllAdminUserMini(companyId);
	}
	
	@Transactional(readOnly = true)
	private final String readCompanyUrl(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final Long readCompanyAdmin(final Long companyId) {
		final Optional<Long> uOptional = companyAccountRepository.findCreatedById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final Preferences readPreferences(final Long companyId) {
		final Optional<Preferences> uOptional = preferencesRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PreferenceForm readPreferenceForm(final Long companyId) {
		final PreferenceForm preferenceForm = new PreferenceForm();
		final Company company = companyRepository.findById(companyId).get();
		final String url = readCompanyUrl(companyId);
		final Preferences preferences = readPreferences(companyId);
		if(preferences != null) {
			preferenceForm.setMessengerId(preferences.getMessengerId());
			preferenceForm.setDisabledMessenger(!preferences.isHasMessenger());
			preferenceForm.parseCommunications(preferences.getCommunications());
			preferenceForm.parsePerspects(preferences.getPerspects());
			preferenceForm.parseMails(preferences.getMails());
		} else {
			final Long messengerId = readCompanyAdmin(companyId);
			preferenceForm.setMessengerId(messengerId);
			preferenceForm.setDisabledMessenger(false);
			preferenceForm.parseDefaultNotification();
		}
		preferenceForm.setId(companyId);
		preferenceForm.setCompanyURL(!StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyMapsiteURL(url) : "");
		preferenceForm.setEmail(company.getCompanymail());
		preferenceForm.setPhone("+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(company.getPhone())));
		preferenceForm.setLanguage(company.getLang());
		preferenceForm.setHasActive(company.isActive());
		preferenceForm.setPublished(company.isPublished());
		return preferenceForm;
	}
	
	@Transactional
	private final Company updateCompanyPreferences(final PreferenceForm preferenceForm) {
		final Company company = companyRepository.findById(preferenceForm.getId()).get();
		company.setLang(preferenceForm.getLanguage());
		company.setActive(preferenceForm.isHasActive());
		return companyRepository.save(company);
	}
	
	@Override
	@Transactional
	public Preferences updatePreferences(final PreferenceForm preferenceForm) {
		if(!preferenceForm.isDisabledMessenger() 
				&& !userRepository.existsByIdAndCompanyId(preferenceForm.getMessengerId(), preferenceForm.getId())) {
			throw new NotFoundException("message.input.notfound");
		}
		updateCompanyPreferences(preferenceForm);
		final Optional<Preferences> uOptional = preferencesRepository.findById(preferenceForm.getId());
		final Preferences preferences = uOptional.isPresent() ? uOptional.get() : new Preferences(preferenceForm.getId());
		preferences.setMessengerId(preferenceForm.isDisabledMessenger() ? readCompanyAdmin(preferenceForm.getId()) : preferenceForm.getMessengerId());
		preferences.setCommunications(preferenceForm.builderCommunicatios());
		preferences.setPerspects(preferenceForm.builderPerspects());
		preferences.setMails(preferenceForm.builderMails());
		preferences.setHasMessenger(!preferenceForm.isDisabledMessenger());
		return preferencesRepository.save(preferences);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasNotificationTalk(final Long companyId, final TalkType type) {
		final Preferences preferences = readPreferences(companyId);
		if(preferences != null) {
			switch(type) {
			case contact: return preferences.getCommunications().charAt(0) == '1';
			case appointment: return preferences.getCommunications().charAt(1) == '1';
			case collaborate: return preferences.getCommunications().charAt(2) == '1';
			case partner: return preferences.getCommunications().charAt(3) == '1';
			case rate: return preferences.getCommunications().charAt(4) == '1';
			case favorite: return preferences.getCommunications().charAt(5) == '1';
			case notice: return preferences.getCommunications().charAt(6) == '1';
			case post: return preferences.getPerspects().charAt(0) == '1';
			case annonce: return preferences.getPerspects().charAt(1) == '1';
			case event: return preferences.getPerspects().charAt(2) == '1';
			case employe: return preferences.getPerspects().charAt(3) == '1';
			case call: return preferences.getPerspects().charAt(4) == '1';
			case mail: return preferences.getPerspects().charAt(5) == '1';
			default: return true;
			}
		}
		return true;
	}
	
	@Transactional(readOnly = true)
	private final boolean hasPremium(final Long companyId) {
		final Optional<Integer> uOptional = premiumRepository.findPremiumPassByCompanyId(companyId, new DateTime(Date.from(Instant.now())));
		return uOptional.isPresent() ? uOptional.get() != 0 : false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasNotificationMail(final Long companyId, final EmailType type) {
		if(!hasPremium(companyId)) return false;
		final Optional<String> uOptional = preferencesRepository.findMailsById(companyId);
		if(uOptional.isPresent()) {
			final String mails = uOptional.get();
			switch(type) {
			case contacts: return mails.charAt(0) == '1';
			case quotes: return mails.charAt(1) == '1';
			case ads: return mails.charAt(2) == '1';
			case infos: return mails.charAt(3) == '1';
			case jobs: return mails.charAt(4) == '1';
			default: return true;
			}
		}
		return true;
	}
	
}
