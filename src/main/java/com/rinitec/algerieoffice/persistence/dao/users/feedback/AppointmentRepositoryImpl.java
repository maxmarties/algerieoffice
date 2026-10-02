package com.rinitec.algerieoffice.persistence.dao.users.feedback;

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

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmAppointLine;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointLine;
import com.rinitec.algerieoffice.web.modal.user.communication.UserAppointLine;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllAppointmentCompanyCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Appointment> root = criteriaQuery.from(Appointment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.between(root.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Root<User> rootUser = criteriaQuery.from(User.class);
			final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
			predicates.add(builder.equal(root.get("userId"), rootUser.get("id")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AppointLine> findAllAppointmentCompanyCriteria(final Long companyId, final Integer filter, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AppointLine> criteriaQuery = builder.createQuery(AppointLine.class);
		final Root<Appointment> rootAppointment = criteriaQuery.from(Appointment.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Boolean> subqueryProfile = criteriaQuery.subquery(Boolean.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<Boolean> subqueryCollaborator = criteriaQuery.subquery(Boolean.class);
		final Root<Collaborator> subrootCollaborator = subqueryCollaborator.from(Collaborator.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final Subquery<String> subqueryAutor = criteriaQuery.subquery(String.class);
		final Root<User> subrootAutor = subqueryAutor.from(User.class);
		final Subquery<Long> subqueryBlacklist = criteriaQuery.subquery(Long.class);
		final Root<BlacklistCompany> subrootBlacklist = subqueryBlacklist.from(BlacklistCompany.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootAppointment.get("companyId"), companyId));
		predicates.add(builder.equal(rootAppointment.get("userId"), rootUser.get("id")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			predicates.add(builder.between(rootAppointment.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		subqueryProfile.select(subrootProfile.get("enabled")).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		subqueryCollaborator.select(subrootCollaborator.get("approuved")).where(builder.equal(subrootCollaborator.get("userId"), rootUser.get("id")));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		final Expression<String> expAutor = builder.concat(subrootAutor.get("firstName"), builder.concat(" ", subrootAutor.get("lastName")));
		subqueryAutor.select(expAutor).where(builder.equal(subrootAutor.get("id"), rootAppointment.get("approuvedBy")));
		subqueryBlacklist.select(subrootBlacklist.get("userId")).where(builder.and(builder.equal(subrootBlacklist.get("companyId"), companyId), 
				builder.equal(subrootBlacklist.get("userId"), rootUser.get("id"))));
		final Expression<Boolean> enabled = subqueryProfile.getSelection();
		final Expression<Boolean> collaborator = subqueryCollaborator.getSelection();
		final Expression<Long> identities = subqueryIdentity.getSelection();
		final Expression<String> autor = subqueryAutor.getSelection();
		final Expression<Long> blacked = subqueryBlacklist.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAppointment.get("postedDate")) : builder.asc(rootAppointment.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAppointment.get("approuved")) : builder.asc(rootAppointment.get("approuved"));
		}
		criteriaQuery.select(builder.construct(AppointLine.class, rootUser, rootAccount.get("pseudo"), collaborator, enabled, identities, rootAppointment, autor, blacked));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AppointLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAppointmentUserCriteria(final Long userId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Appointment> root = criteriaQuery.from(Appointment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("userId"), userId));
		if(filter != null) {
			predicates.add(builder.between(root.get("appointDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
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
	public List<UserAppointLine> findAllAppointmentUserCriteria(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserAppointLine> criteriaQuery = builder.createQuery(UserAppointLine.class);
		final Root<Appointment> rootAppointment = criteriaQuery.from(Appointment.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootAppointment.get("userId"), userId));
		predicates.add(builder.equal(rootAppointment.get("companyId"), rootCompany.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootAppointment.get("appointDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootCompany.get("id")));
		final Expression<String> url = subquerySeo.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAppointment.get("appointDate")) : builder.asc(rootAppointment.get("appointDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootAppointment.get("approuved")) : builder.asc(rootAppointment.get("approuved"));
		}
		criteriaQuery.select(builder.construct(UserAppointLine.class, rootCompany, url, rootAppointment));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<UserAppointLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAppointAdmin(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Appointment> root = criteriaQuery.from(Appointment.class);
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
	public List<AdmAppointLine> findAllAppointAdmin(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmAppointLine> criteriaQuery = builder.createQuery(AdmAppointLine.class);
		final Root<Appointment> rootAppoint = criteriaQuery.from(Appointment.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootAppoint.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootAppoint.get("userId"), rootUser.get("id")));
		if(filter != null) {
			predicates.add(builder.between(rootAppoint.get("postedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(rootCompany.get("tradename")), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAppoint.get("postedDate")) : builder.asc(rootAppoint.get("postedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		default: order = hasDesc ? builder.desc(rootAppoint.get("approuved")) : builder.asc(rootAppoint.get("approuved"));
		}
		criteriaQuery.select(builder.construct(AdmAppointLine.class, rootAppoint, rootCompany, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmAppointLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
