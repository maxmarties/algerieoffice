package com.rinitec.algerieoffice.persistence.dao.company.team;

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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.company.team.GuestLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserContributorLine;

@Repository
public class GuestRepositoryImpl implements GuestRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllGuestCompanyCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Guest> root = criteriaQuery.from(Guest.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.between(root.get("guestDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			final Expression<String> guestname = builder.lower(exp);
			predicates.add(builder.equal(root.get("userId"), rootUser.get("id")));
			predicates.add(builder.like(guestname, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<GuestLine> findAllGuestCompanyCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<GuestLine> criteriaQuery = builder.createQuery(GuestLine.class);
		final Root<Guest> rootGuest = criteriaQuery.from(Guest.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootGuest.get("companyId"), companyId));
		predicates.add(builder.equal(rootGuest.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			predicates.add(builder.between(rootGuest.get("guestDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> guestname = builder.lower(exp);
			predicates.add(builder.like(guestname, "%" + search.toLowerCase() + "%"));
		}
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootGuest.get("guestBy")));
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootGuest.get("guestDate")) : builder.asc(rootGuest.get("guestDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootGuest.get("role")) : builder.asc(rootGuest.get("role"));
		}
		criteriaQuery.select(builder.construct(GuestLine.class, rootUser, rootAccount.get("pseudo"), autor, rootGuest));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<GuestLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllGuestUserCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Guest> root = criteriaQuery.from(Guest.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(builder.between(root.get("guestDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.equal(root.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UserContributorLine> findAllGuestUserCriteria(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserContributorLine> criteriaQuery = builder.createQuery(UserContributorLine.class);
		final Root<Guest> rootGuest = criteriaQuery.from(Guest.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		predicates.add(builder.equal(rootGuest.get("userId"), userId));
		predicates.add(builder.equal(rootGuest.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootGuest.get("guestDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootGuest.get("guestBy")));
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<String> username = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootGuest.get("guestDate")) : builder.asc(rootGuest.get("guestDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootGuest.get("role")) : builder.asc(rootGuest.get("role"));
		}
		criteriaQuery.select(builder.construct(UserContributorLine.class, rootCompany, url, username, rootGuest));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<UserContributorLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
