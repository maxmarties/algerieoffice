package com.rinitec.algerieoffice.persistence.dao.users.profiles;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllLogin(final boolean pro, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAccount.get("userId"), rootUser.get("id")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		predicates.add(pro ? builder.isNotNull(rootUser.get("companyId")) : builder.isNull(rootUser.get("companyId")));
		predicates.add(builder.between(rootAccount.get("lastLoginDate"), begin, end));
		criteriaQuery.select(builder.count(rootAccount)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	
}
