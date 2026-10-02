package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceActivity;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceWilaya;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletter;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmAnnonceLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.AnnonceLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleAnnonceLine;
import com.rinitec.algerieoffice.web.modal.mapsite.AnnonceMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceWidgetMini;

@Repository
public class AnnonceRepositoryImpl implements AnnonceRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllAnnonceCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AnnonceLine> findAllAnnonceCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceLine> criteriaQuery = builder.createQuery(AnnonceLine.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.equal(rootAnnonce.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("type")) : builder.asc(rootAnnonce.get("type")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(AnnonceLine.class, rootAnnonce, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AnnonceLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<PostMini> findAllPublishedAnnonce(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostMini> criteriaQuery = builder.createQuery(PostMini.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		criteriaQuery.select(builder.construct(PostMini.class, root.get("title"), root.get("identify")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("modifiedDate")));
		final TypedQuery<PostMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(100);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerAnnonceCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Object[]> findAllExplorerAnnonceCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<AnnonceDetail> rootDetail = criteriaQuery.from(AnnonceDetail.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.equal(rootAnnonce.get("id"), rootDetail.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("startDate")) : builder.asc(rootAnnonce.get("startDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("endDate")) : builder.asc(rootAnnonce.get("endDate")); break;
		default: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate"));
		}
		criteriaQuery.multiselect(rootAnnonce, rootDetail.get("visibility")).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAnnonceWidget(final SearchAnnonceForm searchAnnonceForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchAnnonceForm.getToken())) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + searchAnnonceForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchAnnonceForm.getKeysword())) {
			final String[] keysword = searchAnnonceForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootAnnonce.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchAnnonceForm.hasPresentFilter()) {
			if(searchAnnonceForm.hasPresentSectors()) {
				final Root<AnnonceActivity> rootActivity = criteriaQuery.from(AnnonceActivity.class);
				predicates.add(builder.equal(rootActivity.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.in(rootActivity.get("sector")).value(searchAnnonceForm.parseSectors()));
			}
			if(searchAnnonceForm.hasPresentWilayas()) {
				final Root<AnnonceWilaya> rootWilaya = criteriaQuery.from(AnnonceWilaya.class);
				predicates.add(builder.equal(rootWilaya.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.or(builder.isNull(rootWilaya.get("wilaya")), 
						builder.in(rootWilaya.get("wilaya")).value(searchAnnonceForm.parseWilayas())));
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenEnd())) {
					predicates.add(builder.between(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin(), 
							searchAnnonceForm.parseDateOpenEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin()));
				}
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseEnd())) {
					predicates.add(builder.between(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin(), 
							searchAnnonceForm.parseDateCloseEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin()));
				}
			}
			if(searchAnnonceForm.hasPresentTypes()) {
				predicates.add(builder.in(rootAnnonce.get("type")).value(searchAnnonceForm.parseTypes()));
			}
			if(searchAnnonceForm.hasPresentDetail()) {
				final Root<AnnonceDetail> rootDetail = criteriaQuery.from(AnnonceDetail.class);
				predicates.add(builder.equal(rootDetail.get("id"), rootAnnonce.get("id")));
				if(searchAnnonceForm.hasPresentVisibilities()) {
					predicates.add(builder.in(rootDetail.get("visibility")).value(searchAnnonceForm.parseVisibilities()));
				}
				if(searchAnnonceForm.hasPresentURL()) {
					predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
				}
				if(searchAnnonceForm.hasPresentFile()) {
					predicates.add(builder.isNotNull(rootDetail.get("fileUUID")));
				}
			}
			if(searchAnnonceForm.getState() != 1) {
				switch(searchAnnonceForm.getState()) {
				case 2: 
					predicates.add(builder.lessThanOrEqualTo(rootAnnonce.get("startDate"), new DateTime(Date.from(Instant.now()))));
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
					break;
				case 3: 
					predicates.add(builder.lessThan(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		criteriaQuery.select(builder.countDistinct(rootAnnonce)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AnnonceWidgetMini> findAnnonceWidgetList(final SearchAnnonceForm searchAnnonceForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceWidgetMini> criteriaQuery = builder.createQuery(AnnonceWidgetMini.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<AnnonceDetail> rootDetail = criteriaQuery.from(AnnonceDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Long> subqueryFavorite = criteriaQuery.subquery(Long.class);
		final Root<FavoriteDocument> subrootFavorite = subqueryFavorite.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesFavorite = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchAnnonceForm.getToken())) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + searchAnnonceForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchAnnonceForm.getKeysword())) {
			final String[] keysword = searchAnnonceForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootAnnonce.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchAnnonceForm.hasPresentFilter()) {
			if(searchAnnonceForm.hasPresentSectors()) {
				final Root<AnnonceActivity> rootActivity = criteriaQuery.from(AnnonceActivity.class);
				predicates.add(builder.equal(rootActivity.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.in(rootActivity.get("sector")).value(searchAnnonceForm.parseSectors()));
			}
			if(searchAnnonceForm.hasPresentWilayas()) {
				final Root<AnnonceWilaya> rootWilaya = criteriaQuery.from(AnnonceWilaya.class);
				predicates.add(builder.equal(rootWilaya.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.or(builder.isNull(rootWilaya.get("wilaya")), 
						builder.in(rootWilaya.get("wilaya")).value(searchAnnonceForm.parseWilayas())));
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenEnd())) {
					predicates.add(builder.between(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin(), 
							searchAnnonceForm.parseDateOpenEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin()));
				}
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseEnd())) {
					predicates.add(builder.between(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin(), 
							searchAnnonceForm.parseDateCloseEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin()));
				}
			}
			if(searchAnnonceForm.hasPresentTypes()) {
				predicates.add(builder.in(rootAnnonce.get("type")).value(searchAnnonceForm.parseTypes()));
			}
			if(searchAnnonceForm.hasPresentVisibilities()) {
				predicates.add(builder.in(rootDetail.get("visibility")).value(searchAnnonceForm.parseVisibilities()));
			}
			if(searchAnnonceForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
			}
			if(searchAnnonceForm.hasPresentFile()) {
				predicates.add(builder.isNotNull(rootDetail.get("fileUUID")));
			}
			if(searchAnnonceForm.getState() != 1) {
				switch(searchAnnonceForm.getState()) {
				case 2: 
					predicates.add(builder.lessThan(rootAnnonce.get("startDate"), new DateTime(Date.from(Instant.now()))));
					predicates.add(builder.greaterThan(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
					break;
				case 3: 
					predicates.add(builder.lessThan(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		predicatesFavorite.add(builder.equal(subrootFavorite.get("documentId"), rootAnnonce.get("id")));
		predicatesFavorite.add(builder.equal(subrootFavorite.get("type"), DocumentType.annonce));
		predicatesFavorite.add(searchAnnonceForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchAnnonceForm.getUserId()) 
				: builder.isNull(subrootFavorite.get("userId")));
		subqueryFavorite.select(subrootFavorite.get("userId")).where(predicatesFavorite.toArray(new Predicate[0]));
		final Expression<Long> favorite = subqueryFavorite.getSelection();
		final Order order;
		switch (searchAnnonceForm.getSort()) {
		case 1: order = searchAnnonceForm.isDesc() ? builder.desc(rootAnnonce.get("startDate")) : builder.asc(rootAnnonce.get("startDate")); break;
		case 2: order = searchAnnonceForm.isDesc() ? builder.desc(rootAnnonce.get("endDate")) : builder.asc(rootAnnonce.get("endDate")); break;
		default: order = searchAnnonceForm.isDesc() ? builder.desc(rootAnnonce.get("type")) : builder.asc(rootAnnonce.get("type"));
		}
		criteriaQuery.select(builder.construct(AnnonceWidgetMini.class, rootAnnonce, rootDetail.get("visibility"), rootCompany, 
				rootSeo.get("url"), favorite, rootAnnonce.get("startDate"), rootAnnonce.get("endDate"), rootAnnonce.get("type"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AnnonceWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchAnnonceForm.getPage() - 1) * searchAnnonceForm.getRow()).setMaxResults(searchAnnonceForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<UUID> findAllAnnonceEasylist(final SearchAnnonceForm searchAnnonceForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUID> criteriaQuery = builder.createQuery(UUID.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<AnnonceDetail> rootDetail = criteriaQuery.from(AnnonceDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchAnnonceForm.getToken())) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + searchAnnonceForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchAnnonceForm.getKeysword())) {
			final String[] keysword = searchAnnonceForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootAnnonce.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchAnnonceForm.hasPresentFilter()) {
			if(searchAnnonceForm.hasPresentSectors()) {
				final Root<AnnonceActivity> rootActivity = criteriaQuery.from(AnnonceActivity.class);
				predicates.add(builder.equal(rootActivity.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.in(rootActivity.get("sector")).value(searchAnnonceForm.parseSectors()));
			}
			if(searchAnnonceForm.hasPresentWilayas()) {
				final Root<AnnonceWilaya> rootWilaya = criteriaQuery.from(AnnonceWilaya.class);
				predicates.add(builder.equal(rootWilaya.get("annonceUUID"), rootAnnonce.get("id")));
				predicates.add(builder.or(builder.isNull(rootWilaya.get("wilaya")), 
						builder.in(rootWilaya.get("wilaya")).value(searchAnnonceForm.parseWilayas())));
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateOpenEnd())) {
					predicates.add(builder.between(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin(), 
							searchAnnonceForm.parseDateOpenEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("startDate"), searchAnnonceForm.parseDateOpenBegin()));
				}
			}
			if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseBegin())) {
				if(!StringUtils.isEmpty(searchAnnonceForm.getDateCloseEnd())) {
					predicates.add(builder.between(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin(), 
							searchAnnonceForm.parseDateCloseEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootAnnonce.get("endDate"), searchAnnonceForm.parseDateCloseBegin()));
				}
			}
			if(searchAnnonceForm.hasPresentTypes()) {
				predicates.add(builder.in(rootAnnonce.get("type")).value(searchAnnonceForm.parseTypes()));
			}
			if(searchAnnonceForm.hasPresentVisibilities()) {
				predicates.add(builder.in(rootDetail.get("visibility")).value(searchAnnonceForm.parseVisibilities()));
			}
			if(searchAnnonceForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
			}
			if(searchAnnonceForm.hasPresentFile()) {
				predicates.add(builder.isNotNull(rootDetail.get("fileUUID")));
			}
			if(searchAnnonceForm.getState() != 1) {
				switch(searchAnnonceForm.getState()) {
				case 2: 
					predicates.add(builder.lessThan(rootAnnonce.get("startDate"), new DateTime(Date.from(Instant.now()))));
					predicates.add(builder.greaterThan(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
					break;
				case 3: 
					predicates.add(builder.lessThan(rootAnnonce.get("endDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		criteriaQuery.select(rootAnnonce.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public List<AnnonceSimultudeMini> findAnnonceProxisList(final Annonce annonce, final List<Integer> sectors, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceSimultudeMini> criteriaQuery = builder.createQuery(AnnonceSimultudeMini.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesProxy = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.notEqual(rootAnnonce.get("id"), annonce.getId()));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		if(!StringUtils.isEmpty(annonce.getKeysword())) {
			final String[] keysword = annonce.getKeysword().split(",");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootAnnonce.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicatesProxy.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(!sectors.isEmpty()) {
			final Root<AnnonceActivity> rootActivity = criteriaQuery.from(AnnonceActivity.class);
			predicatesProxy.add(builder.equal(rootActivity.get("annonceUUID"), rootAnnonce.get("id")));
			predicatesProxy.add(builder.in(rootActivity.get("sector")).value(sectors));
		}
		predicatesProxy.add(builder.equal(rootAnnonce.get("type"), annonce.getType()));
		predicates.add(builder.or(predicatesProxy.toArray(new Predicate[0])));
		criteriaQuery.select(builder.construct(AnnonceSimultudeMini.class, rootAnnonce.get("title"), rootAnnonce.get("identify"), 
				rootSeo.get("url"), rootAnnonce.get("endDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("endDate")), builder.asc(rootAnnonce.get("title")));
		final TypedQuery<AnnonceSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<AnnonceSimultudeMini> findAnnonceSourcesList(final UUID annonceId, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceSimultudeMini> criteriaQuery = builder.createQuery(AnnonceSimultudeMini.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.notEqual(rootAnnonce.get("id"), annonceId));
		criteriaQuery.select(builder.construct(AnnonceSimultudeMini.class, rootAnnonce.get("title"), rootAnnonce.get("identify"), 
				rootSeo.get("url"), rootAnnonce.get("endDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("endDate")), builder.asc(rootAnnonce.get("title")));
		final TypedQuery<AnnonceSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllAnnonceCampaignMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("title")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("title")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmAnnonceCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmAnnonceLine> findAllAdmAnnonceCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmAnnonceLine> criteriaQuery = builder.createQuery(AdmAnnonceLine.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 4: order = hasDesc ? builder.desc(rootAnnonce.get("clickCount")) : builder.asc(rootAnnonce.get("clickCount")); break;
		default: order = hasDesc ? builder.desc(rootAnnonce.get("workCount")) : builder.asc(rootAnnonce.get("workCount"));
		}
		criteriaQuery.select(builder.construct(AdmAnnonceLine.class, rootAnnonce, rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmAnnonceLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllRecycleAnnonceCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<RecycleAnnonceLine> findAllRecycleAnnonceCriteria(Long companyId, Integer filter, String search,
			int sort, int rows, int page, boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<RecycleAnnonceLine> criteriaQuery = builder.createQuery(RecycleAnnonceLine.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.equal(rootAnnonce.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title"));
		}
		criteriaQuery.select(builder.construct(RecycleAnnonceLine.class, rootAnnonce, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<RecycleAnnonceLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countPublishedAnnonce(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllActiveAnnonce() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootAnnonce)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AnnonceMapsite> findAllPostMapsite(int page, int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceMapsite> criteriaQuery = builder.createQuery(AnnonceMapsite.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(AnnonceMapsite.class, rootAnnonce.get("identify"), rootAnnonce.get("modifiedDate"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("modifiedDate")));
		final TypedQuery<AnnonceMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<AnnonceNewsletter> findAllAnnonceNewsletter(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceNewsletter> criteriaQuery = builder.createQuery(AnnonceNewsletter.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(AnnonceNewsletter.class, rootAnnonce.get("id"), rootAnnonce.get("title"), rootAnnonce.get("type")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("modifiedDate")));
		final TypedQuery<AnnonceNewsletter> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllNewsAnnonce(final DateTime begin, final DateTime end, final Integer type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.between(rootAnnonce.get("modifiedDate"), begin, end));
		if(type != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), type));
		}
		criteriaQuery.select(builder.count(rootAnnonce)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AnnonceNewsletterMini> findAllAnnonceNewsletterMini(final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AnnonceNewsletterMini> criteriaQuery = builder.createQuery(AnnonceNewsletterMini.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.in(rootAnnonce.get("id")).value(lines));
		criteriaQuery.select(builder.construct(AnnonceNewsletterMini.class, rootAnnonce, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("modifiedDate")));
		final TypedQuery<AnnonceNewsletterMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAnnonce(final Integer filter, final boolean published) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(rootAnnonce.get("modifiedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(published) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
			predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
			predicates.add(builder.isTrue(rootCompany.get("enabled")));
			predicates.add(builder.isTrue(rootCompany.get("active")));
			predicates.add(builder.isFalse(rootCompany.get("locked")));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootAnnonce));
		} else {
			criteriaQuery.select(builder.count(rootAnnonce)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NewsletterItem> findAllNewsletterItem(final Long companyId, final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsletterItem> criteriaQuery = builder.createQuery(NewsletterItem.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
		predicates.add(builder.equal(rootSeo.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.in(rootAnnonce.get("id")).value(lines));
		criteriaQuery.select(builder.construct(NewsletterItem.class, rootAnnonce, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAnnonce.get("modifiedDate")));
		final TypedQuery<NewsletterItem> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
