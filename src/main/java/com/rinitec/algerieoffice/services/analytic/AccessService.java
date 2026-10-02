package com.rinitec.algerieoffice.services.analytic;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogAnalyticRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.AccessCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.FollowCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.analytic.OutlookRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;
import com.rinitec.algerieoffice.web.listener.events.OnAccessCompanyEvent;

import ua_parser.Client;
import ua_parser.Parser;

@Service
public class AccessService implements IAccessService {

	private AccessCompanyRepository accessCompanyRepository;
	private AnnonceRepository annonceRepository;
	private EmployeRepository employeRepository;
	private EventRepository eventRepository;
	private PostSearchRepository postSearchRepository;
	private BlogRepository blogRepository;
	private BlogAnalyticRepository blogAnalyticRepository;
	
	@Autowired
	public AccessService(AccessCompanyRepository accessCompanyRepository, AnnonceRepository annonceRepository, EmployeRepository employeRepository, 
			EventRepository eventRepository, PostSearchRepository postSearchRepository, BlogRepository blogRepository, BlogAnalyticRepository blogAnalyticRepository, 
			OutlookRepository outlookRepository, FollowCompanyRepository followCompanyRepository) {
		this.accessCompanyRepository = accessCompanyRepository;
		this.annonceRepository = annonceRepository;
		this.employeRepository = employeRepository;
		this.eventRepository = eventRepository;
		this.postSearchRepository = postSearchRepository;
		this.blogRepository = blogRepository;
		this.blogAnalyticRepository = blogAnalyticRepository;
	}
	
	private final String parseDevice(final String userAgent) {
		try {
			final Parser parser = new Parser();
			final Client client = parser.parse(userAgent);
			return client.userAgent.family.concat(" ").concat(client.userAgent.major).concat(" - ").concat(client.os.family).concat(" ").concat(client.os.major)
					.concat(" (").concat(client.device.family.equals("Other") ? "PC" : client.device.family).concat(")");
		} catch(Exception e) {e.printStackTrace();}
		return "";
	}
	
	private final boolean hasFromWeb(final String userAgent) {
		try {
			final Parser parser = new Parser();
			final Client client = parser.parse(userAgent);
			return client.device.family.equals("Other");
		} catch(Exception e) {e.printStackTrace();}
		return false;
	}
	
	@Override
	@Transactional
	public AccessCompany addAccessCompany(final OnAccessCompanyEvent event) {
		final AccessCompany access = new AccessCompany();
		access.setCompanyId(event.getCompanyId());
		access.setUserId(event.getUserId());
		access.setAccessType(event.getAccessType());
		access.setAccessDate(new DateTime(Date.from(Instant.now())));
		access.setDevice(parseDevice(event.getUserAgent()));
		access.setFromWeb(hasFromWeb(event.getUserAgent()));
		return accessCompanyRepository.save(access);
	}
	
	@Override
	@Transactional
	public void incrementClickDocument(final String documentId, final DocumentType type) {
		try {
			final UUID uuid = UUID.fromString(documentId);
			switch(type) {
			case annonce: annonceRepository.incrementClicks(uuid); break;
			case employe: employeRepository.incrementClicks(uuid); break;
			case event: eventRepository.incrementClicks(uuid); break;
			case post: postSearchRepository.incrementClicks(uuid);
			}
		} catch (IllegalArgumentException e) {}
	}
	
	@Override
	@Transactional
	public void incrementWorkDocument(final String documentId, final DocumentType type) {
		try {
			final UUID uuid = UUID.fromString(documentId);
			switch(type) {
			case annonce: annonceRepository.incrementWorks(uuid); break;
			case employe: employeRepository.incrementWorks(uuid); break;
			case post: postSearchRepository.incrementWorks(uuid); break;
			default://EMPTY EVENT
			}
		} catch (IllegalArgumentException e) {}
	}
	
	@Override
	@Transactional
	public void incrementViewBlog(final String blogId) {
		try {
			blogRepository.incrementViews(UUID.fromString(blogId));
		} catch (IllegalArgumentException e) {}
	}
	
	@Override
	@Transactional
	public void incrementMarketBlog(final String blogId) {
		try {
			blogAnalyticRepository.incrementMarket(UUID.fromString(blogId));
		} catch (IllegalArgumentException e) {}
	}
	
}
