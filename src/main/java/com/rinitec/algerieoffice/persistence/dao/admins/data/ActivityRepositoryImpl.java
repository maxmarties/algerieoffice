package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.modal.publics.sectors.ActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorLink;

@Repository
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllActivityCriteria(final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		criteriaQuery.select(builder.count(root));
		if(!StringUtils.isEmpty(filter) || !StringUtils.isEmpty(search)) {
			final List<Predicate> predicates = new ArrayList<Predicate>();
			if(!StringUtils.isEmpty(filter)) {
				predicates.add(builder.equal(root.get("sector"), Integer.valueOf(filter)));
			}
			if(!StringUtils.isEmpty(search)) {
				final Expression<String> code = builder.lower(root.get("code"));
				predicates.add(builder.like(code, "%" + search.toLowerCase() + "%"));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Activity> findAllActivityCriteria(final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Activity> criteriaQuery = builder.createQuery(Activity.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(filter)) {
			predicates.add(builder.equal(root.get("sector"), Integer.valueOf(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> code = builder.lower(root.get("code"));
			predicates.add(builder.like(code, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("code")) : builder.asc(root.get("code")); break;
		case 2: order = hasDesc ? builder.desc(root.get("url")) : builder.asc(root.get("url")); break;
		default: order = hasDesc ? builder.desc(root.get("sector")) : builder.asc(root.get("sector"));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Activity> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<List<String>> findAllChoseActivityCriteria() {
		int index = 1;
		final List<String> codeActivities = new ArrayList<String>();
		final List<List<String>> choseActivities = new ArrayList<List<String>>();
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		criteriaQuery.multiselect(root.get("sector"), root.get("code"));
		criteriaQuery.orderBy(builder.asc(root.get("sector")), builder.asc(root.get("url")));
		final List<Tuple> tuples = entityManager.createQuery(criteriaQuery).getResultList();
		for (final Tuple tuple : tuples) {
			final int sector = (Integer) tuple.get(0);
			if(sector != index) {
				choseActivities.add(new ArrayList<String>(codeActivities));
				codeActivities.clear();
				index = sector;
			}
			codeActivities.add((String) tuple.get(1));
		}
		choseActivities.add(new ArrayList<String>(codeActivities));
		return choseActivities;
	}
	
	@Override
	public List<ActivityLink> findActivityLinkCriteria() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ActivityLink> criteriaQuery = builder.createQuery(ActivityLink.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		criteriaQuery.select(builder.construct(ActivityLink.class, root.get("code"), root.get("url")));
		criteriaQuery.orderBy(builder.asc(root.get("url")));
		final TypedQuery<ActivityLink> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public List<SectorLink> findSectorLinkCriteria(final int sector) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SectorLink> criteriaQuery = builder.createQuery(SectorLink.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Company> subroot = subquery.from(Company.class);
		final Join<Company, Activity> subjoin = subroot.join("activities");
		final List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(subjoin.get("code"), root.get("code")));
		predicates.add(builder.isTrue(subroot.get("enabled")));
		predicates.add(builder.isTrue(subroot.get("active")));
		predicates.add(builder.isFalse(subroot.get("locked")));
		subquery.select(builder.countDistinct(subroot)).where(predicates.toArray(new Predicate[0]));
		final Expression<Long> count = subquery.getSelection();
		criteriaQuery.select(builder.construct(SectorLink.class, root.get("code"), root.get("url"), count)).distinct(true);
		criteriaQuery.where(builder.equal(root.get("sector"), sector)).orderBy(builder.asc(root.get("url")));
		final TypedQuery<SectorLink> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<Tuple> findWilayaActivityLinkCriteria(final int wilaya, final int sector) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		final Root<Activity> root = criteriaQuery.from(Activity.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Company> subroot = subquery.from(Company.class);
		final Join<Company, Activity> subjoinActivity = subroot.join("activities");
		final Join<Company, CompanyAddress> subjoinAddress = subroot.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(subjoinActivity.get("code"), root.get("code")));
		predicates.add(builder.equal(subjoinAddress.get("wilaya"), wilaya));
		predicates.add(builder.isTrue(subroot.get("enabled")));
		predicates.add(builder.isTrue(subroot.get("active")));
		predicates.add(builder.isFalse(subroot.get("locked")));
		subquery.select(builder.countDistinct(subroot)).where(predicates.toArray(new Predicate[0]));
		final Expression<Long> count = subquery.getSelection();
		criteriaQuery.multiselect(root.get("code"), root.get("url"), count).distinct(true);
		criteriaQuery.where(builder.equal(root.get("sector"), sector)).orderBy(builder.asc(root.get("url")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
}
