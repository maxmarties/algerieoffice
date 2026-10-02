package com.rinitec.algerieoffice.persistence.dao.companymaps;

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
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmGuestLine;
import com.rinitec.algerieoffice.web.modal.company.prospect.ProspectLine;

@Repository
public class GuestDocumentRepositoryImpl implements GuestDocumentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllQuoteDocumentCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.post));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootPost.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootDocument)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ProspectLine> findAllQuoteDocumentCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ProspectLine> criteriaQuery = builder.createQuery(ProspectLine.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.post));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootPost.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootDocument.get("consultedBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDocument.get("postedDate")) : builder.asc(rootDocument.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootDocument.get("consulted")) : builder.asc(rootDocument.get("consulted"));
		}
		criteriaQuery.select(builder.construct(ProspectLine.class, rootDocument, rootPost, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ProspectLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdsDocumentCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.annonce));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootAnnonce.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootDocument)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ProspectLine> findAllAdsDocumentCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ProspectLine> criteriaQuery = builder.createQuery(ProspectLine.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Annonce> rootAnnonce = criteriaQuery.from(Annonce.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.annonce));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootAnnonce.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootAnnonce.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootDocument.get("consultedBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDocument.get("postedDate")) : builder.asc(rootDocument.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootAnnonce.get("title")) : builder.asc(rootAnnonce.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootDocument.get("consulted")) : builder.asc(rootDocument.get("consulted"));
		}
		criteriaQuery.select(builder.construct(ProspectLine.class, rootDocument, rootAnnonce, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ProspectLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllInfoDocumentCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.event));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootEvent.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootDocument)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ProspectLine> findAllInfoDocumentCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ProspectLine> criteriaQuery = builder.createQuery(ProspectLine.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Event> rootEvent = criteriaQuery.from(Event.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.event));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootEvent.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEvent.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootDocument.get("consultedBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDocument.get("postedDate")) : builder.asc(rootDocument.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootEvent.get("title")) : builder.asc(rootEvent.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootDocument.get("consulted")) : builder.asc(rootDocument.get("consulted"));
		}
		criteriaQuery.select(builder.construct(ProspectLine.class, rootDocument, rootEvent, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ProspectLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllJobDocumentCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.employe));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootEmploye.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootDocument)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ProspectLine> findAllJobDocumentCriteria(final Long companyId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ProspectLine> criteriaQuery = builder.createQuery(ProspectLine.class);
		final Root<GuestDocument> rootDocument = criteriaQuery.from(GuestDocument.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootDocument.get("companyId"), companyId));
		predicates.add(builder.equal(rootDocument.get("type"), DocumentType.employe));
		predicates.add(builder.equal(rootDocument.get("documentId"), rootEmploye.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootDocument.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootDocument.get("consultedBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootDocument.get("postedDate")) : builder.asc(rootDocument.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootDocument.get("consulted")) : builder.asc(rootDocument.get("consulted"));
		}
		criteriaQuery.select(builder.construct(ProspectLine.class, rootDocument, rootEmploye, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ProspectLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countGuestDocumentCompany(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countGuestCompany(final Long companyId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countGuestCompanyByObject(final Long companyId, final Integer object, final DateTime begin) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(object)));
		predicates.add(builder.greaterThanOrEqualTo(root.get("postedDate"), begin));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countGuestPostCompany(final Long companyId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.equal(root.get("type"), DocumentType.post));
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countGuestAll(final Long companyId, final DateTime begin, final boolean hasPost) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.greaterThanOrEqualTo(root.get("postedDate"), begin));
		if(hasPost) {
			predicates.add(builder.equal(root.get("type"), DocumentType.post));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllGuestAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(root.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(builder.lower(rootCompany.get("tradename")), "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmGuestLine> findAllGuestAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmGuestLine> criteriaQuery = builder.createQuery(AdmGuestLine.class);
		final Root<GuestDocument> rootGuest = criteriaQuery.from(GuestDocument.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootGuest.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootGuest.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(rootCompany.get("tradename")), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootGuest.get("postedDate")) : builder.asc(rootGuest.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootGuest.get("consulted")) : builder.asc(rootGuest.get("consulted"));
		}
		criteriaQuery.select(builder.construct(AdmGuestLine.class, rootGuest, rootCompany));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmGuestLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllGuest(final DateTime begin, final DateTime end, final int type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestDocument> root = criteriaQuery.from(GuestDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(type)));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
