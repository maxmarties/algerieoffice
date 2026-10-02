package com.rinitec.algerieoffice.persistence.dao.users.favorite;

import java.util.ArrayList;
import java.util.List;

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
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEventLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoritePostLine;

@Repository
public class FavoriteDocumentRepositoryImpl implements FavoriteDocumentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllFavoritePostCriteria(final Long userId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.post));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasService = filter.equals("true");
			predicates.add(hasService ? builder.isTrue(rootPost.get("service")) : builder.isFalse(rootPost.get("service")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoritePostLine> findAllFavoritePostCriteria(final Long userId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoritePostLine> criteriaQuery = builder.createQuery(FavoritePostLine.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootDetail = criteriaQuery.from(PostDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.post));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasService = filter.equals("true");
			predicates.add(hasService ? builder.isTrue(rootPost.get("service")) : builder.isFalse(rootPost.get("service")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootPost.get("companyId")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootDetail.get("priceValue")) : builder.asc(rootDetail.get("priceValue")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoritePostLine.class, rootFavorite, rootPost, rootDetail.get("priceType"), 
				rootDetail.get("priceValue"), rootCompany.get("tradename"), url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoritePostLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllFavoriteAnnonceCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.annonce));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootAnnonce.get("id")));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoriteAnnonceLine> findAllFavoriteAnnonceCriteria(final Long userId, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoriteAnnonceLine> criteriaQuery = builder.createQuery(FavoriteAnnonceLine.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.annonce));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootAnnonce.get("id")));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootAnnonce.get("hasTrashed")));
		predicates.add(builder.isTrue(rootAnnonce.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootAnnonce.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootAnnonce.get("companyId")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootAnnonce.get("type")) : builder.asc(rootAnnonce.get("type")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoriteAnnonceLine.class, rootFavorite, rootAnnonce, rootCompany.get("tradename"), url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoriteAnnonceLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllFavoriteEventCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.event));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
			final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
			final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
			subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), rootEvent.get("id")));
			predicates.add(builder.equal(rootCalendar.get("eventUUID"), rootEvent.get("id")));
			predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
			predicates.add(builder.between(rootCalendar.get("eventDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoriteEventLine> findAllFavoriteEventCriteria(final Long userId, final Integer filter, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoriteEventLine> criteriaQuery = builder.createQuery(FavoriteEventLine.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<DateTime> subqueryCalendar = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subrootCalendar = subqueryCalendar.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		subqueryCalendar.select(builder.greatest(subrootCalendar.<DateTime>get("eventDate"))).where(builder.equal(subrootCalendar.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.event));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootEvent.get("id")));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subqueryCalendar.getSelection()));
		predicates.add(builder.isTrue(rootEvent.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.between(rootCalendar.get("eventDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootEvent.get("companyId")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCalendar.get("eventDate")) : builder.asc(rootCalendar.get("eventDate")); break;
		case 2: order = hasDesc ? builder.desc(rootEvent.get("title")) : builder.asc(rootEvent.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootEvent.get("modifiedDate")) : builder.asc(rootEvent.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoriteEventLine.class, rootFavorite, rootEvent, rootCalendar, rootCompany.get("tradename"), url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoriteEventLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllFavoriteEmployeCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.employe));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootEmploye.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootEmploye.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoriteEmployeLine> findAllFavoriteEmployeCriteria(final Long userId, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoriteEmployeLine> criteriaQuery = builder.createQuery(FavoriteEmployeLine.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("type"), DocumentType.employe));
		predicates.add(builder.equal(rootFavorite.get("documentId"), rootEmploye.get("id")));
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootEmploye.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootEmploye.get("companyId")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("expiredDate")) : builder.asc(rootEmploye.get("expiredDate")); break;
		case 2: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoriteEmployeLine.class, rootFavorite, rootEmploye, rootDetail.get("domaine"), rootCompany.get("tradename"), url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoriteEmployeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countFavoriteDocumentByType(final Long companyId, final Integer type, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> rootFavorite = criteriaQuery.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("type"), ParseUtil.parseDocumentType(type)));
		switch(type) {
		case 1: 
			final Root<Post> rootPost = criteriaQuery.from(Post.class);
			predicates.add(builder.equal(rootFavorite.get("documentId"), rootPost.get("id")));
			predicates.add(builder.equal(rootPost.get("companyId"), companyId));
			break;
		case 2:
			final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
			predicates.add(builder.equal(rootFavorite.get("documentId"), rootAnnonce.get("id")));
			predicates.add(builder.equal(rootAnnonce.get("companyId"), companyId));
			break;
		case 3:
			final Root<Event> rootEvent = criteriaQuery.from(Event.class);
			predicates.add(builder.equal(rootFavorite.get("documentId"), rootEvent.get("id")));
			predicates.add(builder.equal(rootEvent.get("companyId"), companyId));
			break;
		default:
			final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
			predicates.add(builder.equal(rootFavorite.get("documentId"), rootEmploye.get("id")));
			predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		}
		if(filter != null) {
			predicates.add(builder.between(rootFavorite.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countFavoriteDocumentUserByType(final Long userId, final Integer type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> root = criteriaQuery.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(type)));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countFavoriteDocumentUser(final Long userId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteDocument> root = criteriaQuery.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
