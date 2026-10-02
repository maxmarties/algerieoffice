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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedCompany;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteCompanyLine;

@Repository
public class FavoriteCompanyRepositoryImpl implements FavoriteCompanyRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllFavoriteCompanyCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteCompany> rootFavorite = criteriaQuery.from(FavoriteCompany.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootFavorite.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(rootFavorite)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<FavoriteCompanyLine> findAllFavoriteCompanyCriteria(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FavoriteCompanyLine> criteriaQuery = builder.createQuery(FavoriteCompanyLine.class);
		final Root<FavoriteCompany> rootFavorite = criteriaQuery.from(FavoriteCompany.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<Integer> subqueryBriefcase = criteriaQuery.subquery(Integer.class);
		final Root<CompanyBriefcase> subrootBriefcase = subqueryBriefcase.from(CompanyBriefcase.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(filter != null) {
			predicates.add(builder.equal(rootFavorite.get("type"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		subqueryBriefcase.select(subrootBriefcase.get("type")).where(builder.equal(subrootBriefcase.get("companyId"), rootCompany.get("id")));
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<Integer> type = subqueryBriefcase.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(rootFavorite.get("type")) : builder.asc(rootFavorite.get("type")); break;
		case 3: order = hasDesc ? builder.desc(rootFavorite.get("alert")) : builder.asc(rootFavorite.get("alert")); break;
		default: order = hasDesc ? builder.desc(rootFavorite.get("postedDate")) : builder.asc(rootFavorite.get("postedDate"));
		}
		criteriaQuery.select(builder.construct(FavoriteCompanyLine.class, rootFavorite, rootCompany, url, type));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<FavoriteCompanyLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllFavoriteUserCompany(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<FavoriteCompany> rootFavorite = criteriaQuery.from(FavoriteCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("companyId"), companyId));
		predicates.add(builder.equal(rootFavorite.get("userId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootFavorite.get("alert")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), rootUser.get("email")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<FollowedCompany> findAllFollowedCompany(final Long userId, final String search, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FollowedCompany> criteriaQuery = builder.createQuery(FollowedCompany.class);
		final Root<FavoriteCompany> rootFavorite = criteriaQuery.from(FavoriteCompany.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("userId"), userId));
		predicates.add(builder.equal(rootFavorite.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.construct(FollowedCompany.class, rootFavorite, rootCompany, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootCompany.get("tradename")));
		final TypedQuery<FollowedCompany> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countFavoriteCompanyByType(final Long companyId, final Integer type, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteCompany> root = criteriaQuery.from(FavoriteCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.equal(root.get("type"), type));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countFavoriteCompanyAlerte(final Long companyId, final Integer filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteCompany> root = criteriaQuery.from(FavoriteCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("alert")));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countFavoriteCompany(final Long companyId, final DateTime begin, final DateTime end) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<FavoriteCompany> root = criteriaQuery.from(FavoriteCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.between(root.get("postedDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UserMini> findAllNewsletterUserCompany(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<FavoriteCompany> rootFavorite = criteriaQuery.from(FavoriteCompany.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootFavorite.get("companyId"), companyId));
		predicates.add(builder.equal(rootFavorite.get("userId"), rootUser.get("id")));
		criteriaQuery.select(builder.construct(UserMini.class, rootUser.get("id"), exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
