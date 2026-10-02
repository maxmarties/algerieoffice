package com.rinitec.algerieoffice.persistence.dao.company.team;

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
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.company.team.AgentLine;

@Repository
public class AgentRepositoryImpl implements AgentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllAgentCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Agent> root = criteriaQuery.from(Agent.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasMale = filter.equals("true");
			predicates.add(hasMale ? builder.isTrue(root.get("sexe")) : builder.isFalse(root.get("sexe")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			final Expression<String> agentname = builder.lower(exp);
			predicates.add(builder.like(agentname, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AgentLine> findAllAgentCriteria(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AgentLine> criteriaQuery = builder.createQuery(AgentLine.class);
		final Root<Agent> root = criteriaQuery.from(Agent.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<User> subroot = subquery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		final Expression<String> username = builder.concat(subroot.get("firstName"), builder.concat(" ", subroot.get("lastName")));
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(filter)) {
			final boolean hasMale = filter.equals("true");
			predicates.add(hasMale ? builder.isTrue(root.get("sexe")) : builder.isFalse(root.get("sexe")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> agentname = builder.lower(exp);
			predicates.add(builder.like(agentname, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(username).where(builder.equal(subroot.get("id"), root.get("userId")));
		final Expression<String> user = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 2: order = hasDesc ? builder.desc(root.get("function")) : builder.asc(root.get("function")); break;
		default: order = hasDesc ? builder.desc(root.get("sexe")) : builder.asc(root.get("sexe"));
		}
		criteriaQuery.select(builder.construct(AgentLine.class, root, user));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AgentLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAgentsForSector(final Integer sector, final Integer wilaya, final Boolean sexe) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<Agent> rootAgent = criteriaQuery.from(Agent.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAgent.get("companyId")));
		if(sector != null) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("sector"), sector));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		if(sexe != null) {
			predicates.add(sexe ? builder.isTrue(rootAgent.get("sexe")) : builder.isFalse(rootAgent.get("sexe")));
		}
		criteriaQuery.select(builder.countDistinct(rootAgent)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAgentsForActivity(final String code, final Integer wilaya, final Boolean sexe) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<Agent> rootAgent = criteriaQuery.from(Agent.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAgent.get("companyId")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		if(sexe != null) {
			predicates.add(sexe ? builder.isTrue(rootAgent.get("sexe")) : builder.isFalse(rootAgent.get("sexe")));
		}
		criteriaQuery.select(builder.countDistinct(rootAgent)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
