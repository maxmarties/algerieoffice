package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmTestimonialLine;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberTestimonial;

@Repository
public class TestimonialRepositoryImpl implements TestimonialRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllTestimonialCriteria(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Testimonial> root = criteriaQuery.from(Testimonial.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("approuved"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(root.get("username"));
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		if(!predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		} else {
			criteriaQuery.select(builder.count(root));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmTestimonialLine> findAllTestimonialCriteria(final Boolean filter, final String search, final int sort, final int rows,
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmTestimonialLine> criteriaQuery = builder.createQuery(AdmTestimonialLine.class);
		final Root<Testimonial> root = criteriaQuery.from(Testimonial.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("approuved"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(root.get("username"));
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("postedDate")) : builder.asc(root.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(root.get("username")) : builder.asc(root.get("username")); break;
		default: order = hasDesc ? builder.desc(root.get("approuved")) : builder.asc(root.get("approuved"));
		}
		criteriaQuery.select(builder.construct(AdmTestimonialLine.class, root));
		if(!predicates.isEmpty()) {
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		criteriaQuery.orderBy(order);
		final TypedQuery<AdmTestimonialLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<MemberTestimonial> findLastMemberTestimonial(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<MemberTestimonial> criteriaQuery = builder.createQuery(MemberTestimonial.class);
		final Root<Testimonial> root = criteriaQuery.from(Testimonial.class);
		criteriaQuery.select(builder.construct(MemberTestimonial.class, root));
		criteriaQuery.where(builder.isTrue(root.get("approuved"))).orderBy(builder.desc(root.get("postedDate")));
		final TypedQuery<MemberTestimonial> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
