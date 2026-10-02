package com.rinitec.algerieoffice.persistence.dao.users;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.users.Identity;
import com.rinitec.algerieoffice.persistence.modal.users.Privilege;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.persistence.result.CompanyAvatar;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmManagerLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmModeratorLine;
import com.rinitec.algerieoffice.web.modal.admins.team.AdmUserLine;
import com.rinitec.algerieoffice.web.modal.company.team.UserLine;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMessenger;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedUser;
import com.rinitec.algerieoffice.web.modal.publics.members.MemberWidgetMini;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<UserMini> findAllAdminMini() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), root.get("email")));
		criteriaQuery.where(builder.isTrue(root.get("admin")));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllUserMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), root.get("email")));
		criteriaQuery.where(builder.equal(root.get("companyId"), companyId));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAdminUserMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Join<User, Role> joinRole = root.join("roles");
		final Join<Role, Privilege> joinPrivilege = joinRole.join("privileges");
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		predicates.add(builder.equal(joinPrivilege.get("name"), "COMPANY_ADMIN_PRIVILEGE"));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public UserMini findAdmSupportMini() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.equal(root.get("email"), ConstraintesURL.EMAIL_ADMINISTRATOR));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public UserMini findSingleUserMini(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), root.get("email")));
		criteriaQuery.where(builder.equal(root.get("id"), userId));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllUserCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			final Join<User, Role> join = root.join("roles");
			predicates.add(builder.equal(join.get("name"), ParseUtil.getRoleName(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UserLine> findAllUserCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserLine> criteriaQuery = builder.createQuery(UserLine.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootUser.get("companyId"), companyId));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			final Join<User, Role> joinRole = rootUser.join("roles");
			predicates.add(builder.equal(joinRole.get("name"), ParseUtil.getRoleName(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAccount.get("createDate")) : builder.asc(rootAccount.get("createDate"));
		}
		criteriaQuery.select(builder.construct(UserLine.class, rootUser, rootAccount));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<UserLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllChoseUserMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.equal(root.get("companyId"), companyId)).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public UserAccountMini findUserAccountMini(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserAccountMini> criteriaQuery = builder.createQuery(UserAccountMini.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Boolean> subqueryProfile = criteriaQuery.subquery(Boolean.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<Boolean> subqueryCollaborator = criteriaQuery.subquery(Boolean.class);
		final Root<Collaborator> subrootCollaborator = subqueryCollaborator.from(Collaborator.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootUser.get("id"), userId));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		subqueryProfile.select(subrootProfile.get("enabled")).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		subqueryCollaborator.select(subrootCollaborator.get("approuved")).where(builder.equal(subrootCollaborator.get("userId"), rootUser.get("id")));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		final Expression<Boolean> profileEnabled = subqueryProfile.getSelection();
		final Expression<Boolean> collaboratorApprouved = subqueryCollaborator.getSelection();
		final Expression<Long> countIdentities = subqueryIdentity.getSelection();
		criteriaQuery.select(builder.construct(UserAccountMini.class, rootUser, rootAccount.get("pseudo"), collaboratorApprouved, 
				profileEnabled, countIdentities));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<UserAccountMini> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public CompanyAvatar readUserAvatar(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyAvatar> criteriaQuery = builder.createQuery(CompanyAvatar.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		criteriaQuery.select(builder.construct(CompanyAvatar.class, exp, root.get("hasAvatar")));
		criteriaQuery.where(builder.equal(root.get("id"), userId));
		final TypedQuery<CompanyAvatar> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllActiveUser() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		predicates.add(builder.isFalse(root.get("admin")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllMembersWidget(final SearchMemberForm searchMemberForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		predicates.add(builder.isFalse(root.get("admin")));
		if(searchMemberForm.hasPresentFilter()) {
			if(!StringUtils.isEmpty(searchMemberForm.getToken())) {
				final Expression<String> username = builder.lower(exp);
				predicates.add(builder.like(username, "%" + searchMemberForm.getToken().toLowerCase() + "%"));
			}
			if(searchMemberForm.getLetter() != null) {
				final Expression<String> username = builder.lower(exp);
				predicates.add(builder.like(username, searchMemberForm.parseLetter() + "%"));
			}
			if(searchMemberForm.hasPresentStatu()) {
				switch(searchMemberForm.getStatu()) {
				case 1: predicates.add(builder.isNotNull(root.get("companyId"))); break;
				case 2: predicates.add(builder.isNull(root.get("companyId")));
				}
			}
			if(searchMemberForm.hasPresentWilaya()) {
				final Root<Profile> rootProfile = criteriaQuery.from(Profile.class);
				predicates.add(builder.equal(root.get("id"), rootProfile.get("userId")));
				predicates.add(builder.equal(rootProfile.get("wilaya"), searchMemberForm.getWilaya()));
			}
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<MemberWidgetMini> findMembersWidgetList(final SearchMemberForm searchMemberForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<MemberWidgetMini> criteriaQuery = builder.createQuery(MemberWidgetMini.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Subquery<Profile> subqueryProfile = criteriaQuery.subquery(Profile.class);
		final Root<Profile> subrootProfile = subqueryProfile.from(Profile.class);
		final Subquery<Long> subqueryFavorite = criteriaQuery.subquery(Long.class);
		final Root<FavoriteAccount> subrootFavorite = subqueryFavorite.from(FavoriteAccount.class);
		final Subquery<Long> subqueryIdentity = criteriaQuery.subquery(Long.class);
		final Root<Identity> subrootIdentity = subqueryIdentity.from(Identity.class);
		final Subquery<String> subqueryCompany = criteriaQuery.subquery(String.class);
		final Root<Company> subrootCompany = subqueryCompany.from(Company.class);
		final Subquery<Long> subqueryPublished = criteriaQuery.subquery(Long.class);
		final Root<Company> subrootPublished = subqueryPublished.from(Company.class);
		final Subquery<String> subquerySeo = criteriaQuery.subquery(String.class);
		final Root<CompanySeo> subrootSeo = subquerySeo.from(CompanySeo.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesFavorite = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		if(searchMemberForm.hasPresentFilter()) {
			if(!StringUtils.isEmpty(searchMemberForm.getToken())) {
				final Expression<String> username = builder.lower(exp);
				predicates.add(builder.like(username, "%" + searchMemberForm.getToken().toLowerCase() + "%"));
			}
			if(searchMemberForm.getLetter() != null) {
				final Expression<String> username = builder.lower(exp);
				predicates.add(builder.like(username, searchMemberForm.parseLetter() + "%"));
			}
			if(searchMemberForm.hasPresentStatu()) {
				switch(searchMemberForm.getStatu()) {
				case 1: predicates.add(builder.isNotNull(rootUser.get("companyId"))); break;
				case 2: predicates.add(builder.isNull(rootUser.get("companyId")));
				}
			}
			if(searchMemberForm.hasPresentWilaya()) {
				final Root<Profile> rootProfile = criteriaQuery.from(Profile.class);
				predicates.add(builder.equal(rootUser.get("id"), rootProfile.get("userId")));
				predicates.add(builder.equal(rootProfile.get("wilaya"), searchMemberForm.getWilaya()));
			}
		}
		subqueryProfile.select(subrootProfile).where(builder.equal(subrootProfile.get("userId"), rootUser.get("id")));
		predicatesFavorite.add(builder.equal(subrootFavorite.get("accountId"), rootUser.get("id")));
		predicatesFavorite.add(searchMemberForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchMemberForm.getUserId()) 
				: builder.isNull(subrootFavorite.get("userId")));
		subqueryFavorite.select(subrootFavorite.get("userId")).where(predicatesFavorite.toArray(new Predicate[0]));
		subqueryIdentity.select(builder.count(subrootIdentity)).where(builder.equal(subrootIdentity.get("identityID").get("userId"), rootUser.get("id")));
		subqueryCompany.select(subrootCompany.get("tradename")).where(builder.equal(subrootCompany.get("id"), rootUser.get("companyId")));
		subquerySeo.select(subrootSeo.get("url")).where(builder.equal(subrootSeo.get("companyId"), rootUser.get("companyId")));
		subqueryPublished.select(subrootPublished.get("id")).where(builder.and(builder.equal(subrootPublished.get("id"), rootUser.get("companyId")), 
				builder.isTrue(subrootPublished.get("enabled")), builder.isTrue(subrootPublished.get("active")), builder.isFalse(subrootPublished.get("locked"))));
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootUser.get("companyId")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Profile> profile = subqueryProfile.getSelection();
		final Expression<Long> favorite = subqueryFavorite.getSelection();
		final Expression<Long> identities = subqueryIdentity.getSelection();
		final Expression<String> tradename = subqueryCompany.getSelection();
		final Expression<String> url = subquerySeo.getSelection();
		final Expression<Long> published = subqueryPublished.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(MemberWidgetMini.class, rootUser, profile, rootAccount.get("pseudo"), favorite, identities, tradename, url, premium, published));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootAccount.get("createDate")));
		final TypedQuery<MemberWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchMemberForm.getPage() - 1) * searchMemberForm.getRow()).setMaxResults(searchMemberForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public ExplorerCompanyMessenger findExplorerCompanyMessenger(final Long userId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ExplorerCompanyMessenger> criteriaQuery = builder.createQuery(ExplorerCompanyMessenger.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(root.get("id"), userId));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		criteriaQuery.select(builder.construct(ExplorerCompanyMessenger.class, root.get("id"), root.get("hasAvatar"), exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<ExplorerCompanyMessenger> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllAdmUserCriteria(final Boolean filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isFalse(root.get("admin")));
		if(filter != null) {
			predicates.add(filter ? builder.isNotNull(root.get("companyId")) : builder.isNull(root.get("companyId")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmUserLine> findAllAdmUserCriteria(final Boolean filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmUserLine> criteriaQuery = builder.createQuery(AdmUserLine.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			predicates.add(filter ? builder.isNotNull(rootUser.get("companyId")) : builder.isNull(rootUser.get("companyId")));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 2: order = hasDesc ? builder.desc(rootAccount.get("createDate")) : builder.asc(rootAccount.get("createDate")); break;
		default: order = hasDesc ? builder.desc(rootAccount.get("ip")) : builder.asc(rootAccount.get("ip")); break;
		}
		criteriaQuery.select(builder.construct(AdmUserLine.class, rootUser, rootAccount));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmUserLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmModeratorCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isFalse(root.get("admin")));
		predicates.add(builder.isNotNull(root.get("companyId")));
		if(filter != null) {
			final Join<User, Role> join = root.join("roles");
			predicates.add(builder.equal(join.get("name"), ParseUtil.getRoleName(filter)));
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
	public List<AdmModeratorLine> findAllAdmModeratorCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmModeratorLine> criteriaQuery = builder.createQuery(AdmModeratorLine.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		predicates.add(builder.isNotNull(rootUser.get("companyId")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		predicates.add(builder.equal(rootUser.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			final Join<User, Role> join = rootUser.join("roles");
			predicates.add(builder.equal(join.get("name"), ParseUtil.getRoleName(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 2: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAccount.get("createDate")) : builder.asc(rootAccount.get("createDate"));
		}
		criteriaQuery.select(builder.construct(AdmModeratorLine.class, rootCompany, rootSeo.get("url"), rootUser, rootAccount.get("numberOfVisits")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmModeratorLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmManagerCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("admin")));
		if(filter != null) {
			final Join<User, Role> join = root.join("roles");
			predicates.add(builder.equal(join.get("name"), ParseUtil.getRoleAdmin(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmManagerLine> findAllAdmManagerCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmManagerLine> criteriaQuery = builder.createQuery(AdmManagerLine.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		predicates.add(builder.isTrue(rootUser.get("admin")));
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		if(filter != null) {
			final Join<User, Role> join = rootUser.join("roles");
			predicates.add(builder.equal(join.get("name"), ParseUtil.getRoleAdmin(filter)));
		}
		if(!StringUtils.isEmpty(search)) {
			predicates.add(builder.like(builder.lower(exp), "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootAccount.get("createDate")) : builder.asc(rootAccount.get("createDate"));
		}
		criteriaQuery.select(builder.construct(AdmManagerLine.class, rootUser, rootAccount));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmManagerLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<FollowedUser> findAllFollowedUser(final Long userId, final Long companyId, final String search, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<FollowedUser> criteriaQuery = builder.createQuery(FollowedUser.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.notEqual(root.get("id"), userId));
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> username = builder.lower(exp);
			predicates.add(builder.like(username, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.construct(FollowedUser.class, root)).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(exp));
		final TypedQuery<FollowedUser> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countActiveUser(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.isFalse(root.get("expired")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<String> findAllNewsletter(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Root<Account> rootAccount = criteriaQuery.from(Account.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootUser.get("id"), rootAccount.get("userId")));
		predicates.add(builder.isTrue(rootUser.get("enabled")));
		predicates.add(builder.isFalse(rootUser.get("locked")));
		predicates.add(builder.isFalse(rootUser.get("expired")));
		predicates.add(builder.isFalse(rootUser.get("admin")));
		predicates.add(builder.isTrue(rootAccount.get("hasAccepte")));
		criteriaQuery.select(rootUser.get("email")).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAccount.get("lastLoginDate")));
		final TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countUserByType(final boolean pro) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(builder.count(root)).where(pro ? builder.isNotNull(root.get("companyId")) : builder.isNull(root.get("companyId")));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
}
