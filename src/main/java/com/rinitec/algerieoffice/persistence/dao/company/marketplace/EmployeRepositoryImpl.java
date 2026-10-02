package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeLocation;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmEmployeLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.EmployeLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleEmployeLine;
import com.rinitec.algerieoffice.web.modal.mapsite.EmployeMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeWidgetMini;

@Repository
public class EmployeRepositoryImpl implements EmployeRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllEmployeCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EmployeLine> findAllEmployeCriteria(final Long companyId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EmployeLine> criteriaQuery = builder.createQuery(EmployeLine.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.equal(rootEmploye.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootEmploye.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title")); break;
		case 2: order = hasDesc ? builder.desc(rootEmploye.get("contract")) : builder.asc(rootEmploye.get("contract")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		case 4: order = hasDesc ? builder.desc(rootEmploye.get("expiredDate")) : builder.asc(rootEmploye.get("expiredDate")); break;
		default: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(EmployeLine.class, rootEmploye, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EmployeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerEmployeCriteria(final Long companyId, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Object[]> findAllExplorerEmployeCriteria(final Long companyId, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("expiredDate")) : builder.asc(rootEmploye.get("expiredDate")); break;
		case 2: order = hasDesc ? builder.desc(rootDetail.get("domaine")) : builder.asc(rootDetail.get("domaine")); break;
		default: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate"));
		}
		criteriaQuery.multiselect(rootEmploye, rootDetail.get("domaine"), rootDetail.get("discoverType"),
				rootDetail.get("discoverValue")).where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEmployeWidget(final SearchEmployeForm searchEmployeForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEmployeForm.getToken())) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + searchEmployeForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEmployeForm.getKeysword())) {
			final String[] keysword = searchEmployeForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEmploye.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEmployeForm.hasPresentFilter()) {
			if(searchEmployeForm.hasPresentDetail()) {
				final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
				predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
				if(searchEmployeForm.hasPresentDomaines()) {
					predicates.add(builder.in(rootDetail.get("domaine")).value(searchEmployeForm.parseDomaines()));
				}
				if(searchEmployeForm.hasPresentURL()) {
					predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
				}
				if(searchEmployeForm.getDiscover() != null) {
					predicates.add(builder.equal(rootDetail.get("discoverType"), searchEmployeForm.getDiscover()));
				}
			}
			if(searchEmployeForm.hasPresentWilayas()) {
				final Root<EmployeLocation> rootLocation = criteriaQuery.from(EmployeLocation.class);
				predicates.add(builder.equal(rootEmploye.get("id"), rootLocation.get("employeUUID")));
				predicates.add(builder.in(rootLocation.get("location")).value(searchEmployeForm.parseWilayas()));
			}
			if(!StringUtils.isEmpty(searchEmployeForm.getDateBegin())) {
				if(!StringUtils.isEmpty(searchEmployeForm.getDateEnd())) {
					predicates.add(builder.between(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin(), 
							searchEmployeForm.parseDateEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin()));
				}
			}
			if(searchEmployeForm.hasPresentTypes()) {
				predicates.add(builder.in(rootEmploye.get("contract")).value(searchEmployeForm.parseTypes()));
			}
			if(searchEmployeForm.getState() != 1) {
				switch(searchEmployeForm.getState()) {
				case 2: predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now())))); break;
				case 3: predicates.add(builder.lessThan(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		criteriaQuery.select(builder.countDistinct(rootEmploye)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EmployeWidgetMini> findEmployeWidgetList(final SearchEmployeForm searchEmployeForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EmployeWidgetMini> criteriaQuery = builder.createQuery(EmployeWidgetMini.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Long> subqueryFavorite = criteriaQuery.subquery(Long.class);
		final Root<FavoriteDocument> subrootFavorite = subqueryFavorite.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesFavorite = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEmployeForm.getToken())) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + searchEmployeForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEmployeForm.getKeysword())) {
			final String[] keysword = searchEmployeForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEmploye.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEmployeForm.hasPresentFilter()) {
			if(searchEmployeForm.hasPresentDomaines()) {
				predicates.add(builder.in(rootDetail.get("domaine")).value(searchEmployeForm.parseDomaines()));
			}
			if(searchEmployeForm.hasPresentWilayas()) {
				final Root<EmployeLocation> rootLocation = criteriaQuery.from(EmployeLocation.class);
				predicates.add(builder.equal(rootEmploye.get("id"), rootLocation.get("employeUUID")));
				predicates.add(builder.in(rootLocation.get("location")).value(searchEmployeForm.parseWilayas()));
			}
			if(!StringUtils.isEmpty(searchEmployeForm.getDateBegin())) {
				if(!StringUtils.isEmpty(searchEmployeForm.getDateEnd())) {
					predicates.add(builder.between(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin(), 
							searchEmployeForm.parseDateEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin()));
				}
			}
			if(searchEmployeForm.hasPresentTypes()) {
				predicates.add(builder.in(rootEmploye.get("contract")).value(searchEmployeForm.parseTypes()));
			}
			if(searchEmployeForm.getDiscover() != null) {
				predicates.add(builder.equal(rootDetail.get("discoverType"), searchEmployeForm.getDiscover()));
			}
			if(searchEmployeForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
			}
			if(searchEmployeForm.getState() != 1) {
				switch(searchEmployeForm.getState()) {
				case 2: predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now())))); break;
				case 3: predicates.add(builder.lessThan(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		predicatesFavorite.add(builder.equal(subrootFavorite.get("documentId"), rootEmploye.get("id")));
		predicatesFavorite.add(builder.equal(subrootFavorite.get("type"), DocumentType.employe));
		predicatesFavorite.add(searchEmployeForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchEmployeForm.getUserId()) 
				: builder.isNull(subrootFavorite.get("userId")));
		subqueryFavorite.select(subrootFavorite.get("userId")).where(predicatesFavorite.toArray(new Predicate[0]));
		final Expression<Long> favorite = subqueryFavorite.getSelection();
		final Order order;
		switch (searchEmployeForm.getSort()) {
		case 1: order = searchEmployeForm.isDesc() ? builder.desc(rootEmploye.get("expiredDate")) : builder.asc(rootEmploye.get("expiredDate")); break;
		case 2: order = searchEmployeForm.isDesc() ? builder.desc(rootDetail.get("domaine")) : builder.asc(rootDetail.get("domaine")); break;
		default: order = searchEmployeForm.isDesc() ? builder.desc(rootEmploye.get("contract")) : builder.asc(rootEmploye.get("contract"));
		}
		criteriaQuery.select(builder.construct(EmployeWidgetMini.class, rootEmploye, rootDetail.get("domaine"), rootDetail.get("discoverType"),
				rootDetail.get("discoverValue"), rootCompany, rootSeo.get("url"), favorite, rootEmploye.get("expiredDate"), rootEmploye.get("contract"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<EmployeWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchEmployeForm.getPage() - 1) * searchEmployeForm.getRow()).setMaxResults(searchEmployeForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<UUID> findAllEmployeEasylist(final SearchEmployeForm searchEmployeForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUID> criteriaQuery = builder.createQuery(UUID.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchEmployeForm.getToken())) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + searchEmployeForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchEmployeForm.getKeysword())) {
			final String[] keysword = searchEmployeForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEmploye.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchEmployeForm.hasPresentFilter()) {
			if(searchEmployeForm.hasPresentDomaines()) {
				predicates.add(builder.in(rootDetail.get("domaine")).value(searchEmployeForm.parseDomaines()));
			}
			if(searchEmployeForm.hasPresentWilayas()) {
				final Root<EmployeLocation> rootLocation = criteriaQuery.from(EmployeLocation.class);
				predicates.add(builder.equal(rootEmploye.get("id"), rootLocation.get("employeUUID")));
				predicates.add(builder.in(rootLocation.get("location")).value(searchEmployeForm.parseWilayas()));
			}
			if(!StringUtils.isEmpty(searchEmployeForm.getDateBegin())) {
				if(!StringUtils.isEmpty(searchEmployeForm.getDateEnd())) {
					predicates.add(builder.between(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin(), 
							searchEmployeForm.parseDateEnd()));
				} else {
					predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), searchEmployeForm.parseDateBegin()));
				}
			}
			if(searchEmployeForm.hasPresentTypes()) {
				predicates.add(builder.in(rootEmploye.get("contract")).value(searchEmployeForm.parseTypes()));
			}
			if(searchEmployeForm.getDiscover() != null) {
				predicates.add(builder.equal(rootDetail.get("discoverType"), searchEmployeForm.getDiscover()));
			}
			if(searchEmployeForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootDetail.get("urlExtern")));
			}
			if(searchEmployeForm.getState() != 1) {
				switch(searchEmployeForm.getState()) {
				case 2: predicates.add(builder.greaterThanOrEqualTo(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now())))); break;
				case 3: predicates.add(builder.lessThan(rootEmploye.get("expiredDate"), new DateTime(Date.from(Instant.now()))));
				}
			}
		}
		criteriaQuery.select(rootEmploye.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public List<EmployeSimultudeMini> findEmployeProxisList(final Employe employe, final Integer domaine, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EmployeSimultudeMini> criteriaQuery = builder.createQuery(EmployeSimultudeMini.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<EmployeDetail> rootDetail = criteriaQuery.from(EmployeDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesProxy = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.notEqual(rootEmploye.get("id"), employe.getId()));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		if(!StringUtils.isEmpty(employe.getKeysword())) {
			final String[] keysword = employe.getKeysword().split(",");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootEmploye.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicatesProxy.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		predicatesProxy.add(builder.equal(rootDetail.get("domaine"), domaine));
		predicatesProxy.add(builder.equal(rootEmploye.get("contract"), employe.getContract()));
		predicates.add(builder.or(predicatesProxy.toArray(new Predicate[0])));
		criteriaQuery.select(builder.construct(EmployeSimultudeMini.class, rootEmploye.get("title"), rootEmploye.get("identify"), 
				rootSeo.get("url"), rootEmploye.get("expiredDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEmploye.get("expiredDate")), builder.asc(rootEmploye.get("title")));
		final TypedQuery<EmployeSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<EmployeSimultudeMini> findEmployeSourcesList(final UUID employeId, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EmployeSimultudeMini> criteriaQuery = builder.createQuery(EmployeSimultudeMini.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.notEqual(rootEmploye.get("id"), employeId));
		criteriaQuery.select(builder.construct(EmployeSimultudeMini.class, rootEmploye.get("title"), rootEmploye.get("identify"), 
				rootSeo.get("url"), rootEmploye.get("expiredDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEmploye.get("expiredDate")), builder.asc(rootEmploye.get("title")));
		final TypedQuery<EmployeSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllAdmEmployeCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(root));
		} else {
			criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AdmEmployeLine> findAllAdmEmployeCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<AdmEmployeLine> criteriaQuery = builder.createQuery(AdmEmployeLine.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		if(filter != null) {
			predicates.add(builder.equal(rootEmploye.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename")); break;
		case 4: order = hasDesc ? builder.desc(rootEmploye.get("clickCount")) : builder.asc(rootEmploye.get("clickCount")); break;
		default: order = hasDesc ? builder.desc(rootEmploye.get("workCount")) : builder.asc(rootEmploye.get("workCount"));
		}
		criteriaQuery.select(builder.construct(AdmEmployeLine.class, rootEmploye, rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<AdmEmployeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllRecycleEmployeCriteria(final Long companyId, final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasTrashed")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<RecycleEmployeLine> findAllRecycleEmployeCriteria(final Long companyId, final Integer filter, final String search,
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<RecycleEmployeLine> criteriaQuery = builder.createQuery(RecycleEmployeLine.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		predicates.add(builder.isTrue(rootEmploye.get("hasTrashed")));
		predicates.add(builder.equal(rootEmploye.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootEmploye.get("contract"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootEmploye.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootEmploye.get("modifiedDate")) : builder.asc(rootEmploye.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootEmploye.get("title")) : builder.asc(rootEmploye.get("title"));
		}
		criteriaQuery.select(builder.construct(RecycleEmployeLine.class, rootEmploye, exp));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<RecycleEmployeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countPublishedEmploye(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllActiveEmploye() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootEmploye)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<EmployeMapsite> findAllPostMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<EmployeMapsite> criteriaQuery = builder.createQuery(EmployeMapsite.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(EmployeMapsite.class, rootEmploye.get("identify"), rootEmploye.get("modifiedDate"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEmploye.get("modifiedDate")));
		final TypedQuery<EmployeMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllEmploye(final Integer filter, final boolean published) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(rootEmploye.get("modifiedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(published) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(rootEmploye.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
			predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
			predicates.add(builder.isTrue(rootCompany.get("enabled")));
			predicates.add(builder.isTrue(rootCompany.get("active")));
			predicates.add(builder.isFalse(rootCompany.get("locked")));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootEmploye));
		} else {
			criteriaQuery.select(builder.count(rootEmploye)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UUIDMini> findAllEmployeNewsletterMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Employe> root = criteriaQuery.from(Employe.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("title")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("title")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public List<NewsletterItem> findAllNewsletterItem(final Long companyId, final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsletterItem> criteriaQuery = builder.createQuery(NewsletterItem.class);
		final Root<Employe> rootEmploye = criteriaQuery.from(Employe.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEmploye.get("companyId"), companyId));
		predicates.add(builder.equal(rootSeo.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootEmploye.get("hasTrashed")));
		predicates.add(builder.isTrue(rootEmploye.get("hasPublished")));
		predicates.add(builder.in(rootEmploye.get("id")).value(lines));
		criteriaQuery.select(builder.construct(NewsletterItem.class, rootEmploye, rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootEmploye.get("modifiedDate")));
		final TypedQuery<NewsletterItem> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
