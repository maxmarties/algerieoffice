package com.rinitec.algerieoffice.persistence.dao.users.easylist;

import java.util.ArrayList;
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
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.easylist.AdmEasylistDocument;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistEventLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistLine;
import com.rinitec.algerieoffice.web.modal.user.easylist.EasylistPostLine;

@Repository
public class EasylistDocumentRepositoryImpl implements EasylistDocumentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllEasylistDocumentsCriteria(final Long userId, final DocumentType type, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<EasylistDocument> root = criteriaQuery.from(EasylistDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.between(root.get("easyDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(root.get("easyname")), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EasylistLine> findAllEasylistDocumentsCriteria(final Long userId, final DocumentType type, final Integer filter,
			final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EasylistLine> criteriaQuery = builder.createQuery(EasylistLine.class);
		final Root<EasylistDocument> root = criteriaQuery.from(EasylistDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.between(root.get("easyDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(root.get("easyname")), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("easyDate")) : builder.asc(root.get("easyDate")); break;
		case 2: order = hasDesc ? builder.desc(root.get("easyname")) : builder.asc(root.get("easyname")); break;
		default: order = hasDesc ? builder.desc(root.get("documents")) : builder.asc(root.get("documents"));
		}
		criteriaQuery.select(builder.construct(EasylistLine.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EasylistLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEasylistPostCriteria(final List<UUID> documents, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(root.get("id")).value(documents));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasService = filter.equals("true");
			predicates.add(hasService ? builder.isTrue(root.get("service")) : builder.isFalse(root.get("service")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EasylistPostLine> findAllEasylistPostCriteria(final List<UUID> documents, final String filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EasylistPostLine> criteriaQuery = builder.createQuery(EasylistPostLine.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootDetail = criteriaQuery.from(PostDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(rootPost.get("id")).value(documents));
		predicates.add(builder.equal(rootPost.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
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
		case 1: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootDetail.get("priceValue")) : builder.asc(rootDetail.get("priceValue")); break;
		default: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EasylistPostLine.class, rootPost, rootDetail.get("priceType"), rootDetail.get("priceValue"), rootCompany, url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EasylistPostLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEasylistAnnonceCriteria(final List<UUID> documents, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Annonce> root = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(root.get("id")).value(documents));
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
	public List<EasylistAnnonceLine> findAllEasylistAnnonceCriteria(final List<UUID> documents, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EasylistAnnonceLine> criteriaQuery = builder.createQuery(EasylistAnnonceLine.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(rootAnnonce.get("id")).value(documents));
		predicates.add(builder.equal(rootAnnonce.get("companyId"), rootCompany.get("id")));
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
		case 1: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("type")) : builder.asc(rootAnnonce.get("type")); break;
		default: order = hasDesc ? builder.desc(rootAnnonce.get("modifiedDate")) : builder.asc(rootAnnonce.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EasylistAnnonceLine.class, rootAnnonce, rootCompany, url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EasylistAnnonceLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEasylistEventCriteria(final List<UUID> documents, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Event> root = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(root.get("id")).value(documents));
		if(filter != null) {
			final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
			final Subquery<DateTime> subquery = criteriaQuery.subquery(DateTime.class);
			final Root<EventCalendar> subroot = subquery.from(EventCalendar.class);
			subquery.select(builder.greatest(subroot.<DateTime>get("eventDate"))).where(builder.equal(subroot.get("eventUUID"), root.get("id")));
			predicates.add(builder.equal(rootCalendar.get("eventUUID"), root.get("id")));
			predicates.add(builder.equal(rootCalendar.get("eventDate"), subquery.getSelection()));
			predicates.add(builder.between(rootCalendar.get("eventDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EasylistEventLine> findAllEasylistEventCriteria(final List<UUID> documents, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EasylistEventLine> criteriaQuery = builder.createQuery(EasylistEventLine.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Root<EventCalendar> rootCalendar = criteriaQuery.from(EventCalendar.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<DateTime> subqueryCalendar = criteriaQuery.subquery(DateTime.class);
		final Root<EventCalendar> subrootCalendar = subqueryCalendar.from(EventCalendar.class);
		final List<Predicate> predicates = new ArrayList<>();
		subqueryCalendar.select(builder.greatest(subrootCalendar.<DateTime>get("eventDate"))).where(builder.equal(subrootCalendar.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.in(rootEvent.get("id")).value(documents));
		predicates.add(builder.equal(rootEvent.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventUUID"), rootEvent.get("id")));
		predicates.add(builder.equal(rootCalendar.get("eventDate"), subqueryCalendar.getSelection()));
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
		case 1: order = hasDesc ? builder.desc(rootEvent.get("title")) : builder.asc(rootEvent.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootCalendar.get("eventDate")) : builder.asc(rootCalendar.get("eventDate")); break;
		default: order = hasDesc ? builder.desc(rootEvent.get("modifiedDate")) : builder.asc(rootEvent.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EasylistEventLine.class, rootEvent, rootCalendar, rootCompany, url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EasylistEventLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEasylistEmployeCriteria(final List<UUID> documents, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(root.get("id")).value(documents));
		if(filter != null) {
			predicates.add(builder.equal(root.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EasylistEmployeLine> findAllEasylistEmployeCriteria(final List<UUID> documents, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EasylistEmployeLine> criteriaQuery = builder.createQuery(EasylistEmployeLine.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.in(rootEmploye.get("id")).value(documents));
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
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
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootEmploye.get("expiredDate")) : builder.asc(rootEmploye.get("expiredDate")); break;
		default: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EasylistEmployeLine.class, rootEmploye, rootDetail.get("domaine"), rootCompany, url));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EasylistEmployeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmEasylistDocumentCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<EasylistDocument> rootEasylist = criteriaQuery.from(EasylistDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(rootEasylist.get("type"), ParseUtil.parseDocumentType(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(rootEasylist.get("userId"), rootUser.get("id")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootEasylist));
		} else {
			criteriaQuery.select(builder.count(rootEasylist)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmEasylistDocument> findAllAdmEasylistDocumentCriteria(final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmEasylistDocument> criteriaQuery = builder.createQuery(AdmEasylistDocument.class);
		final Root<EasylistDocument> rootEasylist = criteriaQuery.from(EasylistDocument.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootEasylist.get("userId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootEasylist.get("type"), ParseUtil.parseDocumentType(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEasylist.get("easyDate")) : builder.asc(rootEasylist.get("easyDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootEasylist.get("documents")) : builder.asc(rootEasylist.get("documents"));
		}
		criteriaQuery.select(builder.construct(AdmEasylistDocument.class, rootEasylist, rootUser, tradename));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmEasylistDocument> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
