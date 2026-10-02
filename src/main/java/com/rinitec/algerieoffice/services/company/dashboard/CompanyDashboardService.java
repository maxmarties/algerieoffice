package com.rinitec.algerieoffice.services.company.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.FollowCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.OutlookRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.AppointmentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.CollaboratorRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.NoticeRepository;
import com.rinitec.algerieoffice.persistence.modal.analytic.FollowCompany;
import com.rinitec.algerieoffice.persistence.modal.analytic.Outlook;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAutentified;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticSearch;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardData;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardDetect;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardJournal;

@Service
public class CompanyDashboardService implements ICompanyDashboardService {

	private CompanyAccountRepository companyAccountRepository;
	private CompanySearchRepository companySearchRepository;
	private OutlookRepository outlookRepository;
	private FollowCompanyRepository followCompanyRepository;
	private NoticeRepository noticeRepository;
	private AppointmentRepository appointmentRepository;
	private CollaboratorRepository collaboratorRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private PostRepository postRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private ActualityRepository actualityRepository;
	private UserRepository userRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private EvaluationRepository evaluationRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	
	@Autowired
	public CompanyDashboardService(CompanyAccountRepository companyAccountRepository, CompanySearchRepository companySearchRepository, 
			OutlookRepository outlookRepository, FollowCompanyRepository followCompanyRepository, NoticeRepository noticeRepository, 
			AppointmentRepository appointmentRepository, CollaboratorRepository collaboratorRepository, AccessCompanyRepository accessCompanyRepository, 
			PostRepository postRepository, AnnonceRepository annonceRepository, EventRepository eventRepository, EmployeRepository employeRepository, 
			ActualityRepository actualityRepository, UserRepository userRepository, JournalCompanyRepository journalCompanyRepository, 
			EvaluationRepository evaluationRepository, FavoriteCompanyRepository favoriteCompanyRepository) {
		this.companyAccountRepository = companyAccountRepository;
		this.companySearchRepository = companySearchRepository;
		this.outlookRepository = outlookRepository;
		this.followCompanyRepository = followCompanyRepository;
		this.noticeRepository = noticeRepository;
		this.appointmentRepository = appointmentRepository;
		this.collaboratorRepository = collaboratorRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.postRepository = postRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.actualityRepository = actualityRepository;
		this.userRepository = userRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.evaluationRepository = evaluationRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
	}
	
	@Transactional(readOnly = true)
	private final Outlook readOutlook(final Long companyId) {
		final Optional<Outlook> uOptional = outlookRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final FollowCompany readFollowCompany(final Long companyId) {
		final Optional<FollowCompany> uOptional = followCompanyRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticSearch readAnalyticSearch(final Long companyId) {
		final Long numberOfVisits = companyAccountRepository.findNumberOfVisitsById(companyId).get();
		final CompanySearch companySearch = companySearchRepository.findById(companyId).get();
		final Outlook outlook = readOutlook(companyId);
		final FollowCompany followCompany = readFollowCompany(companyId);
		final long countNotice = noticeRepository.countByCompanyId(companyId);
		final long countAppoint = appointmentRepository.countByCompanyId(companyId);
		final long countCollaborator = collaboratorRepository.countByCompanyId(companyId);
		return new AnalyticSearch(numberOfVisits, companySearch, outlook, followCompany, countNotice, countAppoint, countCollaborator);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AnalyticAutentified> findAnalyticAutentifiedList(final Long companyId, final int limit) {
		return accessCompanyRepository.findAllAnalyticAutentified(companyId, limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardData> findDashboardDataList(final Long companyId, final boolean published) {
		final List<DashboardData> lines = new ArrayList<DashboardData>();
		final Long countPost = postRepository.countByCompanyId(companyId);
		final Long countPostPublished = published ? postRepository.countPublishedPost(companyId) : 0L;
		final Long countAnnonce = annonceRepository.countByCompanyId(companyId);
		final Long countAnnoncePublished = published ? annonceRepository.countPublishedAnnonce(companyId) : 0L;
		final Long countEvent = eventRepository.countByCompanyId(companyId);
		final Long countEventPublished = published ? eventRepository.countByCompanyIdAndHasPublished(companyId, true) : 0L;
		final Long countEmploye = employeRepository.countByCompanyId(companyId);
		final Long countEmployePublished = published ? employeRepository.countPublishedEmploye(companyId) : 0L;
		final Long countActus = actualityRepository.countByCompanyId(companyId);
		final Long countActusPublished = published ? actualityRepository.countByCompanyIdAndHasPublished(companyId, true) : 0L;
		final Long countUser = userRepository.countByCompanyId(companyId);
		final Long countUserPublished = userRepository.countActiveUser(companyId);
		lines.add(new DashboardData(countPost, countPostPublished));
		lines.add(new DashboardData(countAnnonce, countAnnoncePublished));
		lines.add(new DashboardData(countEvent, countEventPublished));
		lines.add(new DashboardData(countEmploye, countEmployePublished));
		lines.add(new DashboardData(countActus, countActusPublished));
		lines.add(new DashboardData(countUser, countUserPublished));
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardJournal> findLastDashboardJournal(final Long companyId, final int limit) {
		return journalCompanyRepository.findLastJouranlCompany(companyId, limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardDetect> findLastDashboardDetect(final Long companyId, final int limit) {
		return accessCompanyRepository.findLastDashboardDetect(companyId, limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DashboardEvaluation> findLastDashboardEvaluation(final Long companyId, final int limit) {
		return evaluationRepository.findLastDashboardEvaluation(companyId, limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String countFormattedFavoriteCompany(final Long companyId) {
		final long countFavorite = favoriteCompanyRepository.countByCompanyId(companyId);
		return ParseUtil.getFormattedValue(countFavorite);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticAccess readAnalyticAccess(final Long companyId) {
		final Long[] countAccess = new Long[6];
		final Long[] countFavorite = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countAccess[i] = accessCompanyRepository.countAccessCompany(companyId, begin, end);
			countFavorite[i] = favoriteCompanyRepository.countFavoriteCompany(companyId, begin, end);
		}
		return new AdmAnalyticAccess(countAccess, countFavorite);
	}
	
}
