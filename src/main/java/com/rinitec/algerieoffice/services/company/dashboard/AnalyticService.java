package com.rinitec.algerieoffice.services.company.dashboard;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.journal.JournalCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.ContactRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticMarket;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticActivity;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticContact;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticFavorite;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticStats;
import com.rinitec.algerieoffice.web.modal.company.posts.AnalyticPost;

@Service
public class AnalyticService implements IAnalyticService {

	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private AccessCompanyRepository accessCompanyRepository;
	private JournalCompanyRepository journalCompanyRepository;
	private EvaluationRepository evaluationRepository;
	private ContactRepository contactRepository;
	private PostRepository postRepository;
	private PostSearchRepository postSearchRepository;
	private AnnonceRepository annonceRepository;
	private EventRepository eventRepository;
	private EmployeRepository employeRepository;
	private ActualityRepository actualityRepository;
	
	@Autowired
	public AnalyticService(FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, 
			GuestDocumentRepository guestDocumentRepository, AccessCompanyRepository accessCompanyRepository, JournalCompanyRepository journalCompanyRepository, 
			EvaluationRepository evaluationRepository, ContactRepository contactRepository, PostRepository postRepository, PostSearchRepository postSearchRepository, 
			AnnonceRepository annonceRepository, EventRepository eventRepository, EmployeRepository employeRepository, ActualityRepository actualityRepository) {
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.accessCompanyRepository = accessCompanyRepository;
		this.journalCompanyRepository = journalCompanyRepository;
		this.evaluationRepository = evaluationRepository;
		this.contactRepository = contactRepository;
		this.postRepository = postRepository;
		this.postSearchRepository = postSearchRepository;
		this.annonceRepository = annonceRepository;
		this.eventRepository = eventRepository;
		this.employeRepository = employeRepository;
		this.actualityRepository = actualityRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticFavorite readAnalyticFavorite(final Long companyId, final Integer filter) {
		final Integer currFilter = filter != null && filter >= 1 && filter <= 6 ? filter : null;
		final Long[] countFavorites = new Long[4];
		final Long countAlert = favoriteCompanyRepository.countFavoriteCompanyAlerte(companyId, currFilter);
		for(int i = 0; i < 4; i++) {
			countFavorites[i] = favoriteCompanyRepository.countFavoriteCompanyByType(companyId, i + 1, currFilter);
		}
		return new AnalyticFavorite(countAlert, countFavorites);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticFavorite readAnalyticDocument(final Long companyId, final Integer filter) {
		final Integer currFilter = filter != null && filter >= 1 && filter <= 6 ? filter : null;
		final Long[] countFavorites = new Long[4];
		final Long countGuest = guestDocumentRepository.countGuestDocumentCompany(companyId, currFilter);
		for(int i = 0; i < 4; i++) {
			countFavorites[i] = favoriteDocumentRepository.countFavoriteDocumentByType(companyId, i + 1, currFilter);
		}
		return new AnalyticFavorite(countGuest, countFavorites);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticAccess readAnalyticAccess(final Long companyId, final Integer filter) {
		final Integer currFilter = filter != null && filter >= 1 && filter <= 6 ? filter : null;
		final Long[] countAttributs = new Long[4];
		final Long[] countAccess = new Long[12];
		countAttributs[0] = accessCompanyRepository.countAccessCompanyByWeb(companyId, true, currFilter);
		countAttributs[1] = accessCompanyRepository.countAccessCompanyByWeb(companyId, false, currFilter);
		countAttributs[2] = accessCompanyRepository.countAccessCompanyByAuthentified(companyId, true, currFilter);
		countAttributs[3] = accessCompanyRepository.countAccessCompanyByAuthentified(companyId, false, currFilter);
		for(int i = 0; i < 12; i++) {
			countAccess[i] = accessCompanyRepository.countAccessCompanyByType(companyId, i + 1, currFilter);
		}
		return new AnalyticAccess(countAccess, countAttributs);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticActivity readAnalyticActivity(final Long companyId) {
		final Long[] countLogins = new Long[6];
		final Long[] countActivities = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countLogins[i] = journalCompanyRepository.countJournalCompany(companyId, true, begin, end);
			countActivities[i] = journalCompanyRepository.countJournalCompany(companyId, false, begin, end);
		}
		return new AnalyticActivity(countLogins, countActivities);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticEvaluation readAnalyticEvaluation(final Long companyId, final Integer filter) {
		final Integer currFilter = filter != null && filter >= 1 && filter <= 6 ? filter : null;
		return evaluationRepository.findAnalyticEvaluation(companyId, currFilter);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticStats readAnalyticStatsContact(final Long companyId) {
		final Long[] countMonth = new Long[6];
		final Long[] countStats = new Long[4];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countMonth[i] = contactRepository.countContactCompany(companyId, begin, end);
		}
		final DateTime sixsub = ParseUtil.getSubSixmonthForNow();
		for (int i = 0; i < 4; i++) {
			countStats[i] = contactRepository.countContactCompanyByObject(companyId, i + 1, sixsub);
		}
		return new AnalyticStats(countMonth, countStats);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticStats readAnalyticStatsDocument(final Long companyId) {
		final Long[] countMonth = new Long[6];
		final Long[] countStats = new Long[4];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countMonth[i] = guestDocumentRepository.countGuestCompany(companyId, begin, end);
		}
		final DateTime sixsub = ParseUtil.getSubSixmonthForNow();
		for (int i = 0; i < 4; i++) {
			countStats[i] = guestDocumentRepository.countGuestCompanyByObject(companyId, i + 1, sixsub);
		}
		return new AnalyticStats(countMonth, countStats);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticStats readAnalyticStatsPost(final Long companyId) {
		final Long[] countMonth = new Long[6];
		final Long[] countStats = new Long[2];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countMonth[i] = guestDocumentRepository.countGuestPostCompany(companyId, begin, end);
		}
		final DateTime sixsub = ParseUtil.getSubSixmonthForNow();
		for (int i = 0; i < 2; i++) {
			countStats[i] = guestDocumentRepository.countGuestAll(companyId, sixsub, i == 0);
		}
		return new AnalyticStats(countMonth, countStats);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticPost readAnalyticPostActivity(final Long companyId) {
		final Long[] countPost = new Long[6];
		final Long[] countService = new Long[6];
		for (int i = 0; i < 6; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromMonth(5 - i);
			final DateTime end = ParseUtil.getEndDateFromMonth(5 - i);
			countPost[i] = postRepository.countAnalyticPostActivity(companyId, begin, end, false);
			countService[i] = postRepository.countAnalyticPostActivity(companyId, begin, end, true);
		}
		return new AnalyticPost(countPost, countService);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticActivity readCompanyDashboardTrafic(final Long companyId) {
		final Long[] countLogins = new Long[7];
		final Long[] countAccess = new Long[7];
		for (int i = 0; i < 7; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromDay(6 - i);
			final DateTime end = ParseUtil.getEndDateFromDay(6 - i);
			countLogins[i] = journalCompanyRepository.countJournalCompany(companyId, true, begin, end);
			countAccess[i] = accessCompanyRepository.countAccessCompany(companyId, begin, end);
		}
		return new AnalyticActivity(countLogins, countAccess);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticContact readCompanyCommunication(final Long companyId) {
		final Long[] counts = new Long[7];
		for (int i = 0; i < 7; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromDay(6 - i);
			final DateTime end = ParseUtil.getEndDateFromDay(6 - i);
			counts[i] = guestDocumentRepository.countGuestCompany(companyId, begin, end);
		}
		return new AnalyticContact(counts);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticContact readCompanyProspect(final Long companyId) {
		final Long[] counts = new Long[7];
		for (int i = 0; i < 7; i++) {
			final DateTime begin = ParseUtil.getBeginDateFromDay(6 - i);
			final DateTime end = ParseUtil.getEndDateFromDay(6 - i);
			counts[i] = contactRepository.countContactCompany(companyId, begin, end);
		}
		return new AnalyticContact(counts);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticContact readCompanyStatistic(final Long companyId) {
		final Long[] counts = new Long[10];
		final String[] attributs = {"token", "filter", "tag", "simultude", "view"};
		for (int i = 0; i < 10; i++) {
			counts[i] = postSearchRepository.countPostStatistic(companyId, attributs[i / 2], i % 2 == 0);
		}
		return new AnalyticContact(counts);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmAnalyticMarket readCompanyMarket(final Long companyId) {
		final Long[] countAll = new Long[5];
		final Long[] countPublished = new Long[5];
		countAll[0] = postRepository.countByCompanyId(companyId);
		countAll[1] = annonceRepository.countByCompanyId(companyId);
		countAll[2] = eventRepository.countByCompanyId(companyId);
		countAll[3] = employeRepository.countByCompanyId(companyId);
		countAll[4] = actualityRepository.countByCompanyId(companyId);
		countPublished[0] = postRepository.countPublishedPost(companyId);
		countPublished[1] = annonceRepository.countPublishedAnnonce(companyId);
		countPublished[2] = eventRepository.countAllExplorerEventCriteria(companyId, null);
		countPublished[3] = employeRepository.countPublishedEmploye(companyId);
		countPublished[4] = actualityRepository.countAllExplorerActualityCriteria(companyId, null);
		return new AdmAnalyticMarket(countAll, countPublished);
	}
	
}
