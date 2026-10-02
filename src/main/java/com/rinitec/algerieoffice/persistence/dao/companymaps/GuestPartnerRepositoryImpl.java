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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.company.communication.PartnerGuestLine;

@Repository
public class GuestPartnerRepositoryImpl implements GuestPartnerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Long countAllGuestPartnerCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<GuestPartner> root = criteriaQuery.from(GuestPartner.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("partnerId"), companyId));
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
	public List<PartnerGuestLine> findAllGuestPartnerCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PartnerGuestLine> criteriaQuery = builder.createQuery(PartnerGuestLine.class);
		final Root<GuestPartner> rootGuest = criteriaQuery.from(GuestPartner.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<Integer> subqueryBriefcase = criteriaQuery.subquery(Integer.class);
		final Root<CompanyBriefcase> subrootBriefcase = subqueryBriefcase.from(CompanyBriefcase.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootGuest.get("partnerId"), companyId));
		predicates.add(builder.equal(rootGuest.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootGuest.get("guestDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		subqueryBriefcase.select(subrootBriefcase.get("type")).where(builder.equal(subrootBriefcase.get("companyId"), rootCompany.get("id")));
		final Expression<String> exp = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(exp).where(builder.equal(subrootAutor.get("id"), rootGuest.get("approuvedBy")));
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<Integer> type = subqueryBriefcase.getSelection();
		final Expression<String> autor = subqueryAutor.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootGuest.get("guestDate")) : builder.asc(rootGuest.get("guestDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootGuest.get("approuved")) : builder.asc(rootGuest.get("approuved"));
		}
		criteriaQuery.select(builder.construct(PartnerGuestLine.class, rootGuest, rootCompany, url, type, autor));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PartnerGuestLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
