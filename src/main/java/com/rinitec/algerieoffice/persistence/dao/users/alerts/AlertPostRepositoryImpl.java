package com.rinitec.algerieoffice.persistence.dao.users.alerts;

import java.util.ArrayList;
import java.util.List;

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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostLine;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

@Repository
public class AlertPostRepositoryImpl implements AlertPostRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllAlertPostCriteria(final Long userId, final Integer filter, final String search, final DocumentType type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<AlertPost> root = criteriaQuery.from(AlertPost.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AlertPostLine> findAllAlertPostCriteria(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc, final DocumentType type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AlertPostLine> criteriaQuery = builder.createQuery(AlertPostLine.class);
		final Root<AlertPost> root = criteriaQuery.from(AlertPost.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> name = builder.lower(root.get("name"));
			predicates.add(builder.like(name, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("postedDate")) : builder.asc(root.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(root.get("name")) : builder.asc(root.get("name")); break;
		case 3: order = hasDesc ? builder.desc(root.get("sector")) : builder.asc(root.get("sector")); break;
		case 4: order = hasDesc ? builder.desc(root.get("wilaya")) : builder.asc(root.get("wilaya")); break;
		default: order = hasDesc ? builder.desc(root.get("potential")) : builder.asc(root.get("potential"));
		}
		criteriaQuery.select(builder.construct(AlertPostLine.class, root)).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AlertPostLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Object[] countAlertPostUserByType(final Long userId, final Integer type) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<AlertPost> root = criteriaQuery.from(AlertPost.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		predicates.add(builder.equal(root.get("type"), ParseUtil.parseDocumentType(type)));
		criteriaQuery.multiselect(builder.count(root), builder.sumAsLong(root.get("potential"))).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AlertPostResult> findLastAlertPost(final DocumentType type, final int day, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AlertPostResult> criteriaQuery = builder.createQuery(AlertPostResult.class);
		final Root<AlertPost> rootAlert = criteriaQuery.from(AlertPost.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<?> rootPost;
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		switch(type) {
		case post: rootPost = criteriaQuery.from(Post.class); break;
		case annonce: rootPost = criteriaQuery.from(Annonce.class); break;
		case event: rootPost = criteriaQuery.from(Event.class); break;
		default: rootPost = criteriaQuery.from(Employe.class);
		}
		predicates.add(builder.equal(rootAlert.get("type"), type));
		predicates.add(builder.isTrue(rootAlert.get("enabled")));
		predicates.add(builder.or(builder.equal(rootAlert.get("frequency"), 6), builder.equal(rootAlert.get("frequency"), day)));
		predicates.add(builder.equal(rootAlert.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.or(builder.isNull(rootUser.get("companyId")), builder.notEqual(rootUser.get("companyId"), rootCompany.get("id"))));
		predicates.add(builder.equal(rootCompany.get("id"), rootPost.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.greaterThanOrEqualTo(rootPost.get("modifiedDate"), rootAlert.get("postedDate")));
		if(!type.equals(DocumentType.event)) {
			predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		}
		predicates.add(builder.in(joinActivity.get("sector")).value(rootAlert.get("sector")));
		predicates.add(builder.in(joinAddress.get("wilaya")).value(rootAlert.get("wilaya")));
		criteriaQuery.select(builder.construct(AlertPostResult.class, rootAlert, rootUser, builder.count(rootPost)));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).groupBy(rootAlert, rootUser);
		criteriaQuery.orderBy(builder.desc(builder.count(rootPost)), builder.asc(rootAlert.get("potential")));
		final TypedQuery<AlertPostResult> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
