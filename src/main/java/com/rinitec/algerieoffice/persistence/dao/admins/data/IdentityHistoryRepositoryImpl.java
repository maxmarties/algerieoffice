package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.admins.data.IdentityHistory;
import com.rinitec.algerieoffice.persistence.modal.users.User;

@Repository
public class IdentityHistoryRepositoryImpl implements IdentityHistoryRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public List<Object[]> findAllIdentityHistory(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<IdentityHistory> rootHistory = criteriaQuery.from(IdentityHistory.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootHistory.get("companyId"), companyId));
		predicates.add(builder.equal(rootHistory.get("validateBy"), rootUser.get("id")));
		final Expression<String> username = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		criteriaQuery.multiselect(rootHistory, username).where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootHistory.get("historyDate")));
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
}
