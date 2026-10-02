package com.rinitec.algerieoffice.services.explorer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventCalendarRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostPhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.profile.CompanyCreditRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxEvent;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxPost;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxWork;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEvent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePost;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageWork;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetCredit;

@Service
public class ExplorerDetailService implements IExplorerDetailService {

	private EventRepository eventRepository;
	private EventDetailRepository eventDetailRepository;
	private EventCalendarRepository eventCalendarRepository;
	private WorkRepository workRepository;
	private WorkDetailRepository workDetailRepository;
	private PartnerRepository partnerRepository;
	private PostRepository postRepository;
	private PostDetailRepository postDetailRepository;
	private CategoryRepository categoryRepository;
	private PostPhotoRepository postPhotoRepository;
	private CompanyCreditRepository companyCreditRepository;
	private AnnonceRepository annonceRepository;
	private AnnonceDetailRepository annonceDetailRepository;
	private AnnonceActivityRepository annonceActivityRepository;
	private AnnonceWilayaRepository annonceWilayaRepository;
	private EmployeRepository employeRepository;
	private EmployeDetailRepository employeDetailRepository;
	private EmployeLocationRepository employeLocationRepository;
	
	@Autowired
	public ExplorerDetailService(EventRepository eventRepository, EventDetailRepository eventDetailRepository, EventCalendarRepository eventCalendarRepository, 
			WorkRepository workRepository, WorkDetailRepository workDetailRepository, PartnerRepository partnerRepository, PostRepository postRepository, 
			PostDetailRepository postDetailRepository, PostPhotoRepository postPhotoRepository, CategoryRepository categoryRepository, 
			CompanyCreditRepository companyCreditRepository, AnnonceRepository annonceRepository, AnnonceDetailRepository annonceDetailRepository, 
			AnnonceActivityRepository annonceActivityRepository, AnnonceWilayaRepository annonceWilayaRepository, EmployeRepository employeRepository, 
			EmployeDetailRepository employeDetailRepository, EmployeLocationRepository employeLocationRepository) {
		this.eventRepository = eventRepository;
		this.eventDetailRepository = eventDetailRepository;
		this.eventCalendarRepository = eventCalendarRepository;
		this.workRepository = workRepository;
		this.workDetailRepository = workDetailRepository;
		this.partnerRepository = partnerRepository;
		this.postRepository = postRepository;
		this.postDetailRepository = postDetailRepository;
		this.categoryRepository = categoryRepository;
		this.postPhotoRepository = postPhotoRepository;
		this.companyCreditRepository = companyCreditRepository;
		this.annonceRepository = annonceRepository;
		this.annonceDetailRepository = annonceDetailRepository;
		this.annonceActivityRepository = annonceActivityRepository;
		this.annonceWilayaRepository = annonceWilayaRepository;
		this.employeRepository = employeRepository;
		this.employeDetailRepository = employeDetailRepository;
		this.employeLocationRepository = employeLocationRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageEvent readExplorerPageEvent(final ExplorerCurrent explorerCurrent, final String identify) {
		final Optional<Event> uOptional = eventRepository.findEventByCompanyIdAndIdentify(explorerCurrent.getCompanyId(), identify);
		if(uOptional.isPresent()) {
			final Event event = uOptional.get();
			final EventDetail eventDetail = eventDetailRepository.findById(event.getId()).get();
			final List<EventCalendar> eventsCalendar = eventCalendarRepository.findAllByEventUUID(event.getId());
			final ExplorerMeta meta = new ExplorerMeta(explorerCurrent.getCompanyURL(), event, eventDetail);
			final ExplorerInboxEvent inbox = new ExplorerInboxEvent(event, eventDetail, eventsCalendar);
			return new ExplorerPageEvent(meta, inbox);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final String findPartnerName(final UUID partnerId, final Long companyId) {
		final Optional<String> uOptional = partnerRepository.findNameByPartnerIdAndCompanyId(partnerId, companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageWork readExplorerPageWork(final ExplorerCurrent explorerCurrent, final String identify) {
		final Optional<Work> uOptional = workRepository.findWorkByCompanyIdAndIdentify(explorerCurrent.getCompanyId(), identify);
		if(uOptional.isPresent()) {
			final Work work = uOptional.get();
			final WorkDetail workDetail = workDetailRepository.findById(work.getId()).get();
			final String partner = work.getPartnerUUID() != null ? findPartnerName(work.getPartnerUUID(), explorerCurrent.getCompanyId()) : null;
			final ExplorerMeta meta = new ExplorerMeta(explorerCurrent.getCompanyURL(), work, workDetail);
			final ExplorerInboxWork inbox = new ExplorerInboxWork(work, workDetail, partner);
			return new ExplorerPageWork(meta, inbox);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	private final Category readCategory(final UUID categoryId) {
		final Optional<Category> uOptional = categoryRepository.findById(categoryId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final CompanyCredit readCompanyCredit(final Long companyId) {
		final Optional<CompanyCredit> uOptional = companyCreditRepository.findById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPagePost readExplorerPagePost(final ExplorerCurrent explorerCurrent, final String identify) {
		final Optional<Post> uOptional = postRepository.findPostByCompanyIdAndIdentify(explorerCurrent.getCompanyId(), identify);
		if(uOptional.isPresent()) {
			final Post post = uOptional.get();
			final PostDetail postDetail = postDetailRepository.findById(post.getId()).get();
			final List<PostPhoto> postPhotos = postPhotoRepository.findByPostUUID(post.getId());
			final Category category = post.getCategoryId() != null ? readCategory(post.getCategoryId()) : null;
			final String urlCategories = explorerCurrent.getCompanyURL().concat(ConstraintesURL.URL_CATEGORIES);
			final ExplorerMeta meta = new ExplorerMeta(explorerCurrent.getCompanyURL(), post, postPhotos.get(0));
			final ExplorerInboxPost inbox = new ExplorerInboxPost(post, postDetail, postPhotos, category, urlCategories);
			final ExplorerWidgetCredit credit = new ExplorerWidgetCredit(readCompanyCredit(explorerCurrent.getCompanyId()));
			return new ExplorerPagePost(meta, inbox, credit);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageAnnonce readExplorerPageAnnonce(final ExplorerCurrent explorerCurrent, final String identify) {
		final Optional<Annonce> uOptional = annonceRepository.findAnnonceByCompanyIdAndIdentify(explorerCurrent.getCompanyId(), identify);
		if(uOptional.isPresent()) {
			final Annonce annonce = uOptional.get();
			final AnnonceDetail annonceDetail = annonceDetailRepository.findById(annonce.getId()).get();
			final List<Integer> annonceActivities = annonceActivityRepository.findSectorsByAnnonceId(annonce.getId());
			final List<Integer> annonceWilayas = annonceWilayaRepository.findWilayasByAnnonceId(annonce.getId());
			final ExplorerMeta meta = new ExplorerMeta(explorerCurrent.getCompanyURL(), annonce);
			final ExplorerInboxAnnonce inbox = new ExplorerInboxAnnonce(annonce, annonceDetail, annonceActivities, annonceWilayas);
			return new ExplorerPageAnnonce(meta, inbox);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerPageEmploye readExplorerPageEmploye(final ExplorerCurrent explorerCurrent, final String identify) {
		final Optional<Employe> uOptional = employeRepository.findEmployeByCompanyIdAndIdentify(explorerCurrent.getCompanyId(), identify);
		if(uOptional.isPresent()) {
			final Employe employe = uOptional.get();
			final EmployeDetail employeDetail = employeDetailRepository.findById(employe.getId()).get();
			final List<Integer> locations = employeLocationRepository.findLocationsByEmployeId(employe.getId());
			final ExplorerMeta meta = new ExplorerMeta(explorerCurrent.getCompanyURL(), employe);
			final ExplorerInboxEmploye inbox = new ExplorerInboxEmploye(employe, employeDetail, locations);
			return new ExplorerPageEmploye(meta, inbox);
		}
		return null;
	}
	
}
