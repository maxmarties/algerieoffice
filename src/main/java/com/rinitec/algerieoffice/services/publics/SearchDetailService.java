package com.rinitec.algerieoffice.services.publics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventCalendarRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostPhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.web.modal.publics.CompanyAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentsSimultudeList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.ScreenInboxAnnonce;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.ScreenInboxEmploye;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.ScreenInboxEvent;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.ScreenInboxNews;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostSimilarScreen;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.ScreenInboxPost;

@Service
public class SearchDetailService implements ISearchDetailService {

	private CompanyRepository companyRepository;
	private PostRepository postRepository;
	private PostDetailRepository postDetailRepository;
	private PostPhotoRepository postPhotoRepository;
	private PostSearchRepository postSearchRepository;
	private AnnonceRepository annonceRepository;
	private AnnonceDetailRepository annonceDetailRepository;
	private AnnonceActivityRepository annonceActivityRepository;
	private AnnonceWilayaRepository annonceWilayaRepository;
	private EmployeRepository employeRepository;
	private EmployeDetailRepository employeDetailRepository;
	private EmployeLocationRepository employeLocationRepository;
	private EventRepository eventRepository;
	private EventDetailRepository eventDetailRepository;
	private EventCalendarRepository eventCalendarRepository;
	private ActualityRepository actualityRepository;
	
	@Autowired
	public SearchDetailService(CompanyRepository companyRepository, PostRepository postRepository, PostDetailRepository postDetailRepository, 
			PostPhotoRepository postPhotoRepository, PostSearchRepository postSearchRepository, AnnonceRepository annonceRepository, 
			AnnonceDetailRepository annonceDetailRepository, AnnonceActivityRepository annonceActivityRepository, AnnonceWilayaRepository annonceWilayaRepository, 
			EmployeRepository employeRepository, EmployeDetailRepository employeDetailRepository, EmployeLocationRepository employeLocationRepository, 
			EventRepository eventRepository, EventDetailRepository eventDetailRepository, EventCalendarRepository eventCalendarRepository, 
			ActualityRepository actualityRepository) {
		this.companyRepository = companyRepository;
		this.postRepository = postRepository;
		this.postDetailRepository = postDetailRepository;
		this.postPhotoRepository = postPhotoRepository;
		this.postSearchRepository = postSearchRepository;
		this.annonceRepository = annonceRepository;
		this.annonceDetailRepository = annonceDetailRepository;
		this.annonceActivityRepository = annonceActivityRepository;
		this.annonceWilayaRepository = annonceWilayaRepository;
		this.employeRepository = employeRepository;
		this.employeDetailRepository = employeDetailRepository;
		this.employeLocationRepository = employeLocationRepository;
		this.eventRepository = eventRepository;
		this.eventDetailRepository = eventDetailRepository;
		this.eventCalendarRepository = eventCalendarRepository;
		this.actualityRepository = actualityRepository;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public CompanyAutorMini findCompanyAutorMini(final String companyURL) {
		try {
			return companyRepository.findCompanyAutorMini(companyURL);
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ScreenInboxPost readScreenInboxPost(final Long companyId, final String postURL) {
		final Optional<Post> uOptional = postRepository.findPostByCompanyIdAndIdentify(companyId, postURL);
		if(uOptional.isPresent()) {
			final Post post = uOptional.get();
			final PostDetail postDetail = postDetailRepository.findById(post.getId()).get();
			final List<PostPhoto> postPhotos = postPhotoRepository.findByPostUUID(post.getId());
			return new ScreenInboxPost(post, postDetail, postPhotos);
		}
		return null;
	}
	
	private final List<Integer> parseSectors(final Company company) {
		final List<Integer> sectors = new ArrayList<Integer>();
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		for (final Activity activity : activities) {
			sectors.add(activity.getSector());
		}
		return sectors;
	}
	
	private final List<Integer> parseWilayas(final Company company) {
		final List<Integer> wilayas = new ArrayList<Integer>();
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>(company.getAddresses());
		for (final CompanyAddress companyAddress : companiesAddress) {
			wilayas.add(companyAddress.getWilaya());
		}
		return wilayas;
	}
	
	@Transactional
	private final void incrementSimultudes(final List<PostSimultudeMini> proxis) {
		if(!proxis.isEmpty()) {
			final List<UUID> lines = new ArrayList<UUID>();
			for (final PostSimultudeMini proxi : proxis) {
				lines.add(proxi.getPostId());
			}
			postSearchRepository.incrementSimultudes(lines);
		}
	}
	
	@Transactional
	private final void incrementSimilar(final List<PostSimilarScreen> proxis) {
		if(!proxis.isEmpty()) {
			final List<UUID> lines = new ArrayList<UUID>();
			for (final PostSimilarScreen proxi : proxis) {
				lines.add(proxi.getId());
			}
			postSearchRepository.incrementSimultudes(lines);
		}
	}
	
	@Override
	@Transactional
	public DocumentsSimultudeList findPostsSimilarList(final String postId, final Long companyId, final int limit) {
		try {
			final Post post = postRepository.findById(UUID.fromString(postId)).get();
			final Company company = companyRepository.findById(post.getCompanyId()).get();
			final List<PostSimilarScreen> proxis = postRepository.findPostProxisList(post, parseSectors(company), parseWilayas(company), companyId, limit);
			final List<PostSimilarScreen> sources = postRepository.findPostSourcesList(post.getId(), companyId, limit);
			incrementSimilar(proxis);
			return new DocumentsSimultudeList(proxis, sources);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ScreenInboxAnnonce readScreenInboxAnnonce(final Long companyId, final String annonceURL) {
		final Optional<Annonce> uOptional = annonceRepository.findAnnonceByCompanyIdAndIdentify(companyId, annonceURL);
		if(uOptional.isPresent()) {
			final Annonce annonce = uOptional.get();
			final AnnonceDetail annonceDetail = annonceDetailRepository.findById(annonce.getId()).get();
			final List<Integer> annonceActivities = annonceActivityRepository.findSectorsByAnnonceId(annonce.getId());
			final List<Integer> annonceWilayas = annonceWilayaRepository.findWilayasByAnnonceId(annonce.getId());
			return new ScreenInboxAnnonce(annonce, annonceDetail, annonceActivities, annonceWilayas);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DocumentsSimultudeList findAnnoncesSimultudeList(final String annonceId, final Long companyId, final int limit) {
		try {
			final Annonce annonce = annonceRepository.findById(UUID.fromString(annonceId)).get();
			final List<Integer> sectors = annonceActivityRepository.findSectorsByAnnonceId(annonce.getId());
			final List<AnnonceSimultudeMini> proxis = annonceRepository.findAnnonceProxisList(annonce, sectors, companyId, limit);
			final List<AnnonceSimultudeMini> sources = annonceRepository.findAnnonceSourcesList(annonce.getId(), companyId, limit);
			return new DocumentsSimultudeList(proxis, sources);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ScreenInboxEmploye readScreenInboxEmploye(final Long companyId, final String employeURL) {
		final Optional<Employe> uOptional = employeRepository.findEmployeByCompanyIdAndIdentify(companyId, employeURL);
		if(uOptional.isPresent()) {
			final Employe employe = uOptional.get();
			final EmployeDetail employeDetail = employeDetailRepository.findById(employe.getId()).get();
			final List<Integer> locations = employeLocationRepository.findLocationsByEmployeId(employe.getId());
			return new ScreenInboxEmploye(employe, employeDetail, locations);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DocumentsSimultudeList findEmployesSimultudeList(final String employeId, final Long companyId, final int limit) {
		try {
			final Employe employe = employeRepository.findById(UUID.fromString(employeId)).get();
			final Integer domaine = employeDetailRepository.findDomaineById(employe.getId()).get();
			final List<EmployeSimultudeMini> proxis = employeRepository.findEmployeProxisList(employe, domaine, companyId, limit);
			final List<EmployeSimultudeMini> sources = employeRepository.findEmployeSourcesList(employe.getId(), companyId, limit);
			return new DocumentsSimultudeList(proxis, sources);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ScreenInboxEvent readScreenInboxEvent(final Long companyId, final String eventURL) {
		final Optional<Event> uOptional = eventRepository.findEventByCompanyIdAndIdentify(companyId, eventURL);
		if(uOptional.isPresent()) {
			final Event event = uOptional.get();
			final EventDetail eventDetail = eventDetailRepository.findById(event.getId()).get();
			final List<EventCalendar> eventsCalendar = eventCalendarRepository.findAllByEventUUID(event.getId());
			return new ScreenInboxEvent(event, eventDetail, eventsCalendar);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DocumentsSimultudeList findEventsSimultudeList(final String eventId, final Long companyId, final int limit) {
		try {
			final Event event = eventRepository.findById(UUID.fromString(eventId)).get();
			final List<Integer> wilayas = eventCalendarRepository.findAllWilayasByEventUUID(event.getId());
			final List<EventSimultudeMini> proxis = eventRepository.findEventProxisList(event, wilayas, companyId, limit);
			final List<EventSimultudeMini> sources = eventRepository.findEventSourcesList(event.getId(), companyId, limit);
			return new DocumentsSimultudeList(proxis, sources);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ScreenInboxNews findScreenInboxNews(final String actuId) {
		try {
			return actualityRepository.findOneScreenInboxNews(UUID.fromString(actuId));
		} catch (Exception e) {}
		return null;
	}
	
}
