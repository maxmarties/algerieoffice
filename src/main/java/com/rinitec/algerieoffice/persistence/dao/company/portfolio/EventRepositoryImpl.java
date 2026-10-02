package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.persistence.result.EventMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.portfolio.EventLine;
import com.rinitec.algerieoffice.web.modal.mapsite.EventMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventWidgetMini;

@Repository
public class EventRepositoryImpl implements EventRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllEventCriteria(final Long companyId, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> root = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EventLine> findAllEventCriteria(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventLine> criteriaQuery = builder.createQuery(EventLine.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvent.get("companyId"), companyId));
		predicates.add(builder.equal(rootEvent.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootEvent.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEvent.get("title")) : builder.asc(rootEvent.get("title")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootEvent.get("modifiedDate")) : builder.asc(rootEvent.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EventLine.class, rootEvent, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EventLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAutorCriteria(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Event> subroot = subquery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		subquery.select(subroot.get("autorId")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerEventCriteria(final Long companyId, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> root = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EventMini> findAllExplorerEventCriteria(final Long companyId, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventMini> criteriaQuery = builder.createQuery(EventMini.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventDetail> rootDetail = criteriaQuery.from(EventDetail.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.equal(rootDetail.get("id"), rootEvent.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCalendar.get("eventDate")) : builder.asc(rootCalendar.get("eventDate")); break;
		default: order = hasDesc ? builder.desc(rootEvent.get("modifiedDate")) : builder.asc(rootEvent.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EventMini.class, rootEvent.get("title"), rootEvent.get("identify"), 
				rootDetail.get("description"), rootDetail.get("photoUUID"), rootCalendar.get("eventDate"), 
				rootCalendar.get("clockOpen"), rootCalendar.get("clockClose"), rootCalendar.get("wilaya")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EventMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEventWidget(final SearchEventForm searchEventForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEventForm.getToken())) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + searchEventForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEventForm.getKeysword())) {
			final String[] keysword = searchEventForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEvent.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEventForm.hasPresentFilter()) {
			if(searchEventForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchEventForm.parseSectors()));
			}
			if(searchEventForm.hasPresentCalendar()) {
				final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
				final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
				final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
				subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
				predicates.add(builder.equal(rootCalendar.get("eventUUID"), rootEvent.get("id")));
				predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
				if(searchEventForm.hasPresentWilayas()) {
					predicates.add(builder.in(rootCalendar.get("wilaya")).value(searchEventForm.parseWilayas()));
				}
				if(!StringUtils.isEmpty(searchEventForm.getDateBegin())) {
					if(!StringUtils.isEmpty(searchEventForm.getDateEnd())) {
						predicates.add(builder.between(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin(), 
								searchEventForm.parseDateEnd()));
					} else {
						predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin()));
					}
				}
				if(searchEventForm.getClockOpen() != null) {
					switch(searchEventForm.getIndexOpen()) {
					case 1: predicates.add(builder.lessThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
					case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
					case 3: predicates.add(builder.equal(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen()));
					}
				}
				if(searchEventForm.getClockClose() != null) {
					switch(searchEventForm.getIndexClose()) {
					case 1: predicates.add(builder.lessThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
					case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
					case 3: predicates.add(builder.equal(rootCalendar.get("clockClose"), searchEventForm.getClockClose()));
					}
				}
				if(searchEventForm.getState() != 1) {
					switch(searchEventForm.getState()) {
					case 2: predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now())))); break;
					case 3: predicates.add(builder.lessThan(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now()))));
					}
				}
			}
			if(searchEventForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootEvent.get("urlExtern")));
			}
		}
		criteriaQuery.select(builder.countDistinct(rootEvent)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EventWidgetMini> findEventWidgetList(final SearchEventForm searchEventForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventWidgetMini> criteriaQuery = builder.createQuery(EventWidgetMini.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventDetail> rootDetail = criteriaQuery.from(EventDetail.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
		final Subquery<Long> subqueryFavorite = criteriaQuery.subquery(Long.class);
		final Root<FavoriteDocument> subrootFavorite = subqueryFavorite.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesFavorite = new ArrayList<>();
		subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEvent.get("id"), rootCalendar.get("eventUUID")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEventForm.getToken())) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + searchEventForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEventForm.getKeysword())) {
			final String[] keysword = searchEventForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEvent.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEventForm.hasPresentFilter()) {
			if(searchEventForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchEventForm.parseSectors()));
			}
			if(searchEventForm.hasPresentWilayas()) {
				predicates.add(builder.in(rootCalendar.get("wilaya")).value(searchEventForm.parseWilayas()));
			}
			if(!StringUtils.isEmpty(searchEventForm.getDateBegin())) {
				if(!StringUtils.isEmpty(searchEventForm.getDateEnd())) {
					predicates.add(builder.between(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin(), 
							searchEventForm.parseDateEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin()));
				}
			}
			if(searchEventForm.getClockOpen() != null) {
				switch(searchEventForm.getIndexOpen()) {
				case 1: predicates.add(builder.lessThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
				case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
				case 3: predicates.add(builder.equal(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen()));
				}
			}
			if(searchEventForm.getClockClose() != null) {
				switch(searchEventForm.getIndexClose()) {
				case 1: predicates.add(builder.lessThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
				case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
				case 3: predicates.add(builder.equal(rootCalendar.get("clockClose"), searchEventForm.getClockClose()));
				}
			}
			if(searchEventForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootEvent.get("urlExtern")));
			}
			if(searchEventForm.getState() != 1) {
				switch(searchEventForm.getState()) {
				case 2: predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now())))); break;
				case 3: predicates.add(builder.lessThan(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		predicatesFavorite.add(builder.equal(subrootFavorite.get("documentId"), rootEvent.get("id")));
		predicatesFavorite.add(builder.equal(subrootFavorite.get("type"), DocumentType.event));
		predicatesFavorite.add(searchEventForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchEventForm.getUserId()) 
				: builder.isNull(subrootFavorite.get("userId")));
		subqueryFavorite.select(subrootFavorite.get("userId")).where(predicatesFavorite.toArray(new Predicate[0]));
		final Expression<Long> favorite = subqueryFavorite.getSelection();
		final Order order;
		switch (searchEventForm.getSort()) {
		case 1: order = searchEventForm.isDesc() ? builder.desc(rootCalendar.get("eventDate")) : builder.asc(rootCalendar.get("eventDate")); break;
		case 2: order = searchEventForm.isDesc() ? builder.desc(rootCalendar.get("clockOpen")) : builder.asc(rootCalendar.get("clockOpen")); break;
		default: order = searchEventForm.isDesc() ? builder.desc(rootCalendar.get("clockClose")) : builder.asc(rootCalendar.get("clockClose"));
		}
		criteriaQuery.select(builder.construct(EventWidgetMini.class, rootEvent, rootCalendar, rootDetail.get("photoUUID"),
				rootDetail.get("description"), rootCompany, rootSeo.get("url"), favorite, rootCalendar.get("eventDate"), rootCalendar.get("clockOpen"), 
				rootCalendar.get("clockClose"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EventWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchEventForm.getPage() - 1) * searchEventForm.getRow()).setMaxResults(searchEventForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<UUID> findAllEventEasylist(final SearchEventForm searchEventForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUID> criteriaQuery = builder.createQuery(UUID.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("id"), rootCalendar.get("eventUUID")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEventForm.getToken())) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + searchEventForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEventForm.getKeysword())) {
			final String[] keysword = searchEventForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEvent.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEventForm.hasPresentFilter()) {
			if(searchEventForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchEventForm.parseSectors()));
			}
			if(searchEventForm.hasPresentWilayas()) {
				predicates.add(builder.in(rootCalendar.get("wilaya")).value(searchEventForm.parseWilayas()));
			}
			if(!StringUtils.isEmpty(searchEventForm.getDateBegin())) {
				if(!StringUtils.isEmpty(searchEventForm.getDateEnd())) {
					predicates.add(builder.between(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin(), 
							searchEventForm.parseDateEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), searchEventForm.parseDateBegin()));
				}
			}
			if(searchEventForm.getClockOpen() != null) {
				switch(searchEventForm.getIndexOpen()) {
				case 1: predicates.add(builder.lessThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
				case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen())); break;
				case 3: predicates.add(builder.equal(rootCalendar.get("clockOpen"), searchEventForm.getClockOpen()));
				}
			}
			if(searchEventForm.getClockClose() != null) {
				switch(searchEventForm.getIndexClose()) {
				case 1: predicates.add(builder.lessThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
				case 2: predicates.add(builder.greaterThan(rootCalendar.get("clockClose"), searchEventForm.getClockClose())); break;
				case 3: predicates.add(builder.equal(rootCalendar.get("clockClose"), searchEventForm.getClockClose()));
				}
			}
			if(searchEventForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootEvent.get("urlExtern")));
			}
			if(searchEventForm.getState() != 1) {
				switch(searchEventForm.getState()) {
				case 2: predicates.add(builder.greaterThanOrEqualTo(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now())))); break;
				case 3: predicates.add(builder.lessThan(rootCalendar.get("eventDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		criteriaQuery.select(rootEvent.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public List<EventSimultudeMini> findEventProxisList(final Event event, final List<Integer> wilayas, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventSimultudeMini> criteriaQuery = builder.createQuery(EventSimultudeMini.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesProxy = new ArrayList<>();
		subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("id"), rootCalendar.get("eventUUID")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.notEqual(rootEvent.get("id"), event.getId()));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		if(!StringUtils.isEmpty(event.getKeysword())) {
			final String[] keysword = event.getKeysword().split(",");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEvent.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicatesProxy.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		predicatesProxy.add(builder.in(rootCalendar.get("wilaya")).value(wilayas));
		predicates.add(builder.or(predicatesProxy.toArray(new Predicate[0])));
		criteriaQuery.select(builder.construct(EventSimultudeMini.class, rootEvent.get("title"), rootEvent.get("identify"), 
				rootSeo.get("url"), rootCalendar.get("eventDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootCalendar.get("eventDate")), builder.asc(rootEvent.get("title")));
		final TypedQuery<EventSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<EventSimultudeMini> findEventSourcesList(final UUID eventId, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventSimultudeMini> criteriaQuery = builder.createQuery(EventSimultudeMini.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("companyId"), companyId));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootEvent.get("id"), rootCalendar.get("eventUUID")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.notEqual(rootEvent.get("id"), eventId));
		criteriaQuery.select(builder.construct(EventSimultudeMini.class, rootEvent.get("title"), rootEvent.get("identify"), 
				rootSeo.get("url"), rootCalendar.get("eventDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootCalendar.get("eventDate")), builder.asc(rootEvent.get("title")));
		final TypedQuery<EventSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllEventCampaignMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Event> root = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("title")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("title")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActiveEvent() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootEvent)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EventMapsite> findAllPostMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EventMapsite> criteriaQuery = builder.createQuery(EventMapsite.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(EventMapsite.class, rootEvent.get("identify"), rootEvent.get("modifiedDate"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEvent.get("modifiedDate")));
		final TypedQuery<EventMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEvent(final Integer filter, final boolean published) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(rootEvent.get("modifiedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(published) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
			predicates.add(builder.isTrue(rootCompany.get("enabled")));
			predicates.add(builder.isTrue(rootCompany.get("active")));
			predicates.add(builder.isFalse(rootCompany.get("locked")));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootEvent));
		} else {
			criteriaQuery.select(builder.count(rootEvent)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NewsletterItem> findAllNewsletterItem(final Long companyId, final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsletterItem> criteriaQuery = builder.createQuery(NewsletterItem.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventDetail> rootDetail = criteriaQuery.from(EventDetail.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEvent.get("companyId"), companyId));
		predicates.add(builder.equal(rootEvent.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootSeo.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.in(rootEvent.get("id")).value(lines));
		criteriaQuery.select(builder.construct(NewsletterItem.class, rootEvent, rootDetail.get("description"), rootDetail.get("photoUUID"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEvent.get("modifiedDate")));
		final TypedQuery<NewsletterItem> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
