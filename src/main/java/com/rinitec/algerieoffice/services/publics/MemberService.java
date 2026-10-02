package com.rinitec.algerieoffice.services.publics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.SocialProvider;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.TestimonialRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySeoRepository;
import com.rinitec.algerieoffice.persistence.dao.users.IdentityRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ReportRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.AccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.IdentityID;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberProfile;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberTestimonial;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.members.MembersWidgetList;
import com.rinitec.algerieoffice.web.modal.user.account.PrivateProfile;

@Service
public class MemberService implements IMemberService {

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private ProfileRepository profileRepository;
	private IdentityRepository identityRepository;
	private CompanyRepository companyRepository;
	private CompanySeoRepository companySeoRepository;
	private AppointmentRepository appointmentRepository;
	private EvaluationRepository evaluationRepository;
	private NoticeRepository noticeRepository;
	private ReportRepository reportRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private TestimonialRepository testimonialRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public MemberService(UserRepository userRepository, AccountRepository accountRepository, ProfileRepository profileRepository, 
			IdentityRepository identityRepository, CompanyRepository companyRepository, CompanySeoRepository companySeoRepository, 
			AppointmentRepository appointmentRepository, EvaluationRepository evaluationRepository, NoticeRepository noticeRepository, 
			ReportRepository reportRepository, FavoriteAccountRepository favoriteAccountRepository, FavoriteCompanyRepository favoriteCompanyRepository, 
			FavoriteDocumentRepository favoriteDocumentRepository, TestimonialRepository testimonialRepository, ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.profileRepository = profileRepository;
		this.identityRepository = identityRepository;
		this.companyRepository = companyRepository;
		this.companySeoRepository = companySeoRepository;
		this.appointmentRepository = appointmentRepository;
		this.evaluationRepository = evaluationRepository;
		this.noticeRepository = noticeRepository;
		this.reportRepository = reportRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.testimonialRepository = testimonialRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countAllActiveMember() {
		return userRepository.countAllActiveUser();
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findPsuedoByUserId(final Long userId) {
		final Optional<String> uOptional = accountRepository.findPseudoById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final Profile readProfile(final Long userId) {
		final Optional<Profile> uOptional = profileRepository.findById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final int readReactivity(final Long userId) {
		final long countCompany = companyRepository.countByEnabled(true);
		final long countReactivity = appointmentRepository.countByUserId(userId) + evaluationRepository.countByUserId(userId) 
			+ noticeRepository.countByUserId(userId) + reportRepository.countByUserId(userId) + favoriteDocumentRepository.countByUserId(userId);
		final int reactivity = (int) ((countReactivity * 100) / countCompany);
		return reactivity > 100 ? 100 : reactivity;
	}
	
	@Transactional(readOnly = true)
	private final int readPopularity(final Long userId) {
		final long countUser = userRepository.countByEnabled(true);
		final long countPopularity = favoriteAccountRepository.countByAccountId(userId);
		final int popularity = (int) ((countPopularity * 100) / countUser);
		return popularity > 100 ? 100 : popularity;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrivateProfile readPrivateProfile(final User user) {
		final Long userId = user.getId();
		final Account account = accountRepository.findById(userId).get();
		final Profile profile = readProfile(user.getId());
		final boolean idFacebook = identityRepository.existsById(new IdentityID(userId, SocialProvider.FACEBOOK.getProviderType()));
		final boolean idGoogle = identityRepository.existsById(new IdentityID(userId, SocialProvider.GOOGLE.getProviderType()));
		final boolean idLinkedin = identityRepository.existsById(new IdentityID(userId, SocialProvider.LINKEDIN.getProviderType()));
		final long favoriteAccount = favoriteAccountRepository.countByUserId(userId);
		final long favoriteCompany = favoriteCompanyRepository.countByUserId(userId);
		final int reactivity = readReactivity(userId);
		final int popularity = readPopularity(userId);
		return new PrivateProfile(user, account, profile, idFacebook, idGoogle, idLinkedin, favoriteAccount, favoriteCompany, reactivity, popularity);
	}
	
	@Transactional(readOnly = true)
	private final String readTradename(final Long companyId) {
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final String readURL(final Long companyId) {
		final Optional<String> uOptional = companySeoRepository.findUrlById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public MemberProfile readMemberProfile(final String pseudo) {
		final Account account = accountRepository.findByPseudo(pseudo);
		if(account != null) {
			final Long userId = account.getUserId();
			final User user = userRepository.findById(userId).get();
			if(user.isMember()) {
				//final Long companyId = user.getCompanyId();
				final Company company = user.getCompanyId() != null ? companyRepository.findById(user.getCompanyId()).get() : null;
				final Profile profile = readProfile(userId);
				final boolean idFacebook = identityRepository.existsById(new IdentityID(userId, SocialProvider.FACEBOOK.getProviderType()));
				final boolean idGoogle = identityRepository.existsById(new IdentityID(userId, SocialProvider.GOOGLE.getProviderType()));
				final boolean idLinkedin = identityRepository.existsById(new IdentityID(userId, SocialProvider.LINKEDIN.getProviderType()));
				final long favoriteAccount = favoriteAccountRepository.countByUserId(userId);
				final long favoriteCompany = favoriteCompanyRepository.countByUserId(userId);
				final int reactivity = readReactivity(userId);
				final int popularity = readPopularity(userId);
				final String tradename = company != null ? company.getTradename() : null;
				final String url = company != null && company.isPublished() ? readURL(company.getId()) : null;
				final boolean hasOnline = activeUserStore.hasLogged(user.getEmail());
				return new MemberProfile(user, account, profile, idFacebook, idGoogle, idLinkedin, favoriteAccount, favoriteCompany, 
						reactivity, popularity, tradename, url, hasOnline);
			}
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public MembersWidgetList findMembersWidgetList(final SearchMemberForm searchMemberForm) {
		final Long countResult = userRepository.countAllMembersWidget(searchMemberForm);
		final List<MemberWidgetMini> lines = countResult == 0L ? new ArrayList<MemberWidgetMini>() 
				: userRepository.findMembersWidgetList(searchMemberForm);
		for (final MemberWidgetMini line : lines) {
			final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
			line.updateOnline(hasOnline);
		}
		return new MembersWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MemberTestimonial> findLastMemberTestimonial(final int limit) {
		return testimonialRepository.findLastMemberTestimonial(limit);
	}
	
}
