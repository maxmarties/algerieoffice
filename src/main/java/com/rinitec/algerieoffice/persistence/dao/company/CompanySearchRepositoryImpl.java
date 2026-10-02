package com.rinitec.algerieoffice.persistence.dao.company;

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

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyLogoMini;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanySimultudeLine;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetB2C;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetMini;

@Repository
public class CompanySearchRepositoryImpl implements CompanySearchRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllCompanySimultude(final Long companyId, final Integer sector, final Integer wilaya) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final Join<Company, Activity> joinActivity = root.join("activities");
		final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(root.get("id"), companyId));
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		predicates.add(builder.equal(joinActivity.get("sector"), sector));
		predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CompanySimultudeLine> findAllCompanySimultude(final Long companyId, final Integer sector, final Integer wilaya, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanySimultudeLine> criteriaQuery = builder.createQuery(CompanySimultudeLine.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Join<Company, Activity> joinActivity = rootCompany.join("activities");
		final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootSearch.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.equal(joinActivity.get("sector"), sector));
		predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		criteriaQuery.select(builder.construct(CompanySimultudeLine.class, rootCompany.get("id"), rootCompany.get("tradename"), 
				rootSeo.get("url"), rootSearch.get("simultude"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(rootSearch.get("simultude")), builder.asc(rootCompany.get("tradename")));
		final TypedQuery<CompanySimultudeLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllCompanyWidget(final String code, final Integer wilaya, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = root.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(root.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CompanyWidgetMini> findCompanyWidgetList(final Long userId, final String code, final Integer wilaya, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetMini> criteriaQuery = builder.createQuery(CompanyWidgetMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Subquery<Double> subqueryNote = criteriaQuery.subquery(Double.class);
		final Root<Evaluation> subrootNote = subqueryNote.from(Evaluation.class);
		final Subquery<Long> subqueryEvaluation = criteriaQuery.subquery(Long.class);
		final Root<Evaluation> subrootEvaluation = subqueryEvaluation.from(Evaluation.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootAccount.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryNote.select(builder.avg(subrootNote.get("note"))).where(builder.equal(subrootNote.get("companyId"), rootCompany.get("id")));
		subqueryEvaluation.select(builder.count(subrootEvaluation)).where(builder.equal(subrootEvaluation.get("companyId"), rootCompany.get("id")));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				userId != null ? builder.equal(subrootFavorite.get("userId"), userId) : builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Double> note = subqueryNote.getSelection();
		final Expression<Long> evaluation = subqueryEvaluation.getSelection();
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootAccount.get("modifiedDate")) : builder.asc(rootAccount.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootCompany.get("buildDate")) : builder.asc(rootCompany.get("buildDate")); break;
		case 3: order = hasDesc ? builder.desc(rootAccount.get("numberOfVisits")) : builder.asc(rootAccount.get("numberOfVisits")); break;
		default: order = hasDesc ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename"));
		}
		criteriaQuery.select(builder.construct(CompanyWidgetMini.class, rootCompany, rootSeo.get("url"), note, evaluation, favorite, premium, 
				rootCompany.get("tradename"), rootCompany.get("buildDate"), rootAccount.get("modifiedDate"), rootAccount.get("numberOfVisits"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<CompanyWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<Long> findAllCompanyEasylist(final String code, final Integer wilaya, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(code)) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicates.add(builder.equal(joinActivity.get("code"), code));
		}
		if(wilaya != null) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(rootCompany.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public Long countAllCompanyWidget(final SearchCompanyForm searchCompanyForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> root = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("enabled")));
		predicates.add(builder.isTrue(root.get("active")));
		predicates.add(builder.isFalse(root.get("locked")));
		if(!StringUtils.isEmpty(searchCompanyForm.getToken())) {
			final Expression<String> tradename = builder.lower(root.get("tradename"));
			predicates.add(builder.like(tradename, "%" + searchCompanyForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchCompanyForm.getKeysword())) {
			final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
			final String[] keysword = searchCompanyForm.parseKeysword();
			final List<Predicate> predicatesSeo = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootSeo.get("keysword"));
				predicatesSeo.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.equal(rootSeo.get("companyId"), root.get("id")));
			predicates.add(builder.or(predicatesSeo.toArray(new Predicate[0])));
		}
		if(searchCompanyForm.hasPresentFilter()) {
			if(searchCompanyForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = root.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchCompanyForm.parseSectors()));
			}
			if(searchCompanyForm.hasPresentCompanyAddress()) {
				final Join<Company, CompanyAddress> joinAddress = root.join("addresses");
				if(!StringUtils.isEmpty(searchCompanyForm.getPostal())) {
					predicates.add(searchCompanyForm.isEqualPostal() 
							? builder.equal(joinAddress.get("postal"), searchCompanyForm.getPostal()) 
									: builder.notEqual(joinAddress.get("postal"), searchCompanyForm.getPostal()));
				} else {
					predicates.add(builder.in(joinAddress.get("wilaya")).value(searchCompanyForm.parseWilayas()));
				}
			}
			if(!StringUtils.isEmpty(searchCompanyForm.getDateBegin())) {
				switch(searchCompanyForm.getIndexDate()) {
				case 1: predicates.add(builder.lessThan(root.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 2: predicates.add(builder.greaterThan(root.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 3: predicates.add(builder.between(root.get("buildDate"), searchCompanyForm.parseDateBegin(), searchCompanyForm.parseDateEnd()));
				}
			}
			if(searchCompanyForm.hasPresentCompanyBriefcase()) {
				final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
				predicates.add(builder.equal(rootBriefcase.get("companyId"), root.get("id")));
				if(searchCompanyForm.hasPresentTypes()) {
					predicates.add(builder.in(rootBriefcase.get("type")).value(searchCompanyForm.parseTypes()));
				}
				if(searchCompanyForm.hasPresentBriefcases()) {
					predicates.add(builder.in(rootBriefcase.get("briefcase")).value(searchCompanyForm.parseBriefcases()));
				}
				if(searchCompanyForm.getWarehouse() != null) {
					switch(searchCompanyForm.getWarehouse()) {
					case 1: predicates.add(builder.isFalse(rootBriefcase.get("warehouse"))); break;
					case 2: predicates.add(builder.isTrue(rootBriefcase.get("warehouse")));
					}
				}
				if(!StringUtils.isEmpty(searchCompanyForm.getCapital())) {
					switch(searchCompanyForm.getIndexCapital()) {
					case 1: predicates.add(builder.greaterThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 2: predicates.add(builder.lessThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 3: predicates.add(builder.equal(rootBriefcase.get("capital"), searchCompanyForm.parseCapital()));
					}
				}
			}
			if(searchCompanyForm.hasPresentCredits()) {
				final Root<CompanyCredit> rootCredit = criteriaQuery.from(CompanyCredit.class);
				final List<Predicate> predicatesCredit = new ArrayList<>();
				for(int i = 0; i < 5; i++) {
					if(searchCompanyForm.getCredits()[i]) {
						switch(i + 1) {
						case 1: predicatesCredit.add(builder.isTrue(rootCredit.get("cheque"))); break;
						case 2: predicatesCredit.add(builder.isTrue(rootCredit.get("versement"))); break;
						case 3: predicatesCredit.add(builder.isTrue(rootCredit.get("espece"))); break;
						case 4: predicatesCredit.add(builder.isTrue(rootCredit.get("carte"))); break;
						default: predicatesCredit.add(builder.isTrue(rootCredit.get("paypal")));
						}
					}
				}
				predicates.add(builder.equal(rootCredit.get("companyId"), root.get("id")));
				predicates.add(builder.or(predicatesCredit.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.hasPresentDigitals()) {
				if(searchCompanyForm.hasPresentSocials()) {
					final Root<CompanyLinked> rootLinked = criteriaQuery.from(CompanyLinked.class);
					final List<Predicate> predicatesLinked = new ArrayList<>();
					predicatesLinked.add(builder.isNotNull(rootLinked.get("facebook")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("twitter")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("linkedin")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("youtube")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("google")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("instagram")));
					predicates.add(builder.equal(rootLinked.get("companyId"), root.get("id")));
					predicates.add(builder.or(predicatesLinked.toArray(new Predicate[0])));
				}
				if(searchCompanyForm.hasPresentWebsites()) {
					final Subquery<Long> subqueryLinked = criteriaQuery.subquery(Long.class);
					final Root<CompanyLinked> subrootLinked = subqueryLinked.from(CompanyLinked.class);
					final Join<CompanyLinked, LinkedWebsite> subjoinWebiste = subrootLinked.join("websites");
					subqueryLinked.select(builder.count(subjoinWebiste)).where(builder.equal(subrootLinked.get("companyId"), root.get("id")));
					final Expression<Long> countWebsite = subqueryLinked.getSelection();
					predicates.add(builder.greaterThan(countWebsite, 0L));
				}
				if(searchCompanyForm.hasPresentCarte()) {
					final Root<CompanyLocation> rootLocation = criteriaQuery.from(CompanyLocation.class);
					predicates.add(builder.equal(rootLocation.get("companyId"), root.get("id")));
				}
			}
			if(searchCompanyForm.hasPresentContacts()) {
				final Root<CompanyShedule> rootShedule = criteriaQuery.from(CompanyShedule.class);
				predicates.add(builder.equal(rootShedule.get("companyId"), root.get("id")));
				if(searchCompanyForm.getContacts()[2]) {
					predicates.add(builder.isNotNull(rootShedule.get("mobile")));
				}
				if(searchCompanyForm.getContacts()[3]) {
					predicates.add(builder.isNotNull(rootShedule.get("fax")));
				}
			}
			if(searchCompanyForm.hasPresentLanguages()) {
				final List<Predicate> predicatesLanguage = new ArrayList<>();
				if(searchCompanyForm.getLanguages()[0]) {
					predicatesLanguage.add(builder.equal(root.get("lang"), "fr"));
				}
				if(searchCompanyForm.getLanguages()[1]) {
					predicatesLanguage.add(builder.equal(root.get("lang"), "en"));
				}
				if(searchCompanyForm.getLanguages()[2]) {
					predicatesLanguage.add(builder.equal(root.get("lang"), "ar"));
				}
				predicates.add(builder.or(predicatesLanguage.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.getWarehouse() != null && searchCompanyForm.getWarehouse() == 3) {
				final Subquery<Long> subqueryAddress = criteriaQuery.subquery(Long.class);
				final Root<Company> subrootAddress = subqueryAddress.from(Company.class);
				final Join<Company, CompanyAddress> subjoinAddress = subrootAddress.join("addresses");
				subqueryAddress.select(builder.count(subjoinAddress)).where(builder.equal(subrootAddress.get("id"), root.get("id")));
				final Expression<Long> countAddress = subqueryAddress.getSelection();
				predicates.add(builder.greaterThan(countAddress, 1L));
			}
		}
		criteriaQuery.select(builder.countDistinct(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CompanyWidgetMini> findCompanyWidgetList(final SearchCompanyForm searchCompanyForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetMini> criteriaQuery = builder.createQuery(CompanyWidgetMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootAccount.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchCompanyForm.getToken())) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + searchCompanyForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchCompanyForm.getKeysword())) {
			final String[] keysword = searchCompanyForm.parseKeysword();
			final List<Predicate> predicatesSeo = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootSeo.get("keysword"));
				predicatesSeo.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesSeo.toArray(new Predicate[0])));
		}
		if(searchCompanyForm.hasPresentFilter()) {
			if(searchCompanyForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchCompanyForm.parseSectors()));
			}
			if(searchCompanyForm.hasPresentCompanyAddress()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				if(!StringUtils.isEmpty(searchCompanyForm.getPostal())) {
					predicates.add(searchCompanyForm.isEqualPostal() 
							? builder.equal(joinAddress.get("postal"), searchCompanyForm.getPostal()) 
									: builder.notEqual(joinAddress.get("postal"), searchCompanyForm.getPostal()));
				} else {
					predicates.add(builder.in(joinAddress.get("wilaya")).value(searchCompanyForm.parseWilayas()));
				}
			}
			if(!StringUtils.isEmpty(searchCompanyForm.getDateBegin())) {
				switch(searchCompanyForm.getIndexDate()) {
				case 1: predicates.add(builder.lessThan(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 2: predicates.add(builder.greaterThan(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 3: predicates.add(builder.between(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin(), searchCompanyForm.parseDateEnd()));
				}
			}
			if(searchCompanyForm.hasPresentCompanyBriefcase()) {
				final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
				predicates.add(builder.equal(rootBriefcase.get("companyId"), rootCompany.get("id")));
				if(searchCompanyForm.hasPresentTypes()) {
					predicates.add(builder.in(rootBriefcase.get("type")).value(searchCompanyForm.parseTypes()));
				}
				if(searchCompanyForm.hasPresentBriefcases()) {
					predicates.add(builder.in(rootBriefcase.get("briefcase")).value(searchCompanyForm.parseBriefcases()));
				}
				if(searchCompanyForm.getWarehouse() != null) {
					switch(searchCompanyForm.getWarehouse()) {
					case 1: predicates.add(builder.isFalse(rootBriefcase.get("warehouse"))); break;
					case 2: predicates.add(builder.isTrue(rootBriefcase.get("warehouse")));
					}
				}
				if(!StringUtils.isEmpty(searchCompanyForm.getCapital())) {
					switch(searchCompanyForm.getIndexCapital()) {
					case 1: predicates.add(builder.greaterThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 2: predicates.add(builder.lessThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 3: predicates.add(builder.equal(rootBriefcase.get("capital"), searchCompanyForm.parseCapital()));
					}
				}
			}
			if(searchCompanyForm.hasPresentCredits()) {
				final Root<CompanyCredit> rootCredit = criteriaQuery.from(CompanyCredit.class);
				final List<Predicate> predicatesCredit = new ArrayList<>();
				for(int i = 0; i < 5; i++) {
					if(searchCompanyForm.getCredits()[i]) {
						switch(i + 1) {
						case 1: predicatesCredit.add(builder.isTrue(rootCredit.get("cheque"))); break;
						case 2: predicatesCredit.add(builder.isTrue(rootCredit.get("versement"))); break;
						case 3: predicatesCredit.add(builder.isTrue(rootCredit.get("espece"))); break;
						case 4: predicatesCredit.add(builder.isTrue(rootCredit.get("carte"))); break;
						default: predicatesCredit.add(builder.isTrue(rootCredit.get("paypal")));
						}
					}
				}
				predicates.add(builder.equal(rootCredit.get("companyId"), rootCompany.get("id")));
				predicates.add(builder.or(predicatesCredit.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.hasPresentDigitals()) {
				if(searchCompanyForm.hasPresentSocials()) {
					final Root<CompanyLinked> rootLinked = criteriaQuery.from(CompanyLinked.class);
					final List<Predicate> predicatesLinked = new ArrayList<>();
					predicatesLinked.add(builder.isNotNull(rootLinked.get("facebook")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("twitter")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("linkedin")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("youtube")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("google")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("instagram")));
					predicates.add(builder.equal(rootLinked.get("companyId"), rootCompany.get("id")));
					predicates.add(builder.or(predicatesLinked.toArray(new Predicate[0])));
				}
				if(searchCompanyForm.hasPresentWebsites()) {
					final Subquery<Long> subqueryLinked = criteriaQuery.subquery(Long.class);
					final Root<CompanyLinked> subrootLinked = subqueryLinked.from(CompanyLinked.class);
					final Join<CompanyLinked, LinkedWebsite> subjoinWebiste = subrootLinked.join("websites");
					subqueryLinked.select(builder.count(subjoinWebiste)).where(builder.equal(subrootLinked.get("companyId"), rootCompany.get("id")));
					final Expression<Long> countWebsite = subqueryLinked.getSelection();
					predicates.add(builder.greaterThan(countWebsite, 0L));
				}
				if(searchCompanyForm.hasPresentCarte()) {
					final Root<CompanyLocation> rootLocation = criteriaQuery.from(CompanyLocation.class);
					predicates.add(builder.equal(rootLocation.get("companyId"), rootCompany.get("id")));
				}
			}
			if(searchCompanyForm.hasPresentContacts()) {
				final Root<CompanyShedule> rootShedule = criteriaQuery.from(CompanyShedule.class);
				predicates.add(builder.equal(rootShedule.get("companyId"), rootCompany.get("id")));
				if(searchCompanyForm.getContacts()[2]) {
					predicates.add(builder.isNotNull(rootShedule.get("mobile")));
				}
				if(searchCompanyForm.getContacts()[3]) {
					predicates.add(builder.isNotNull(rootShedule.get("fax")));
				}
			}
			if(searchCompanyForm.hasPresentLanguages()) {
				final List<Predicate> predicatesLanguage = new ArrayList<>();
				if(searchCompanyForm.getLanguages()[0]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "fr"));
				}
				if(searchCompanyForm.getLanguages()[1]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "en"));
				}
				if(searchCompanyForm.getLanguages()[2]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "ar"));
				}
				predicates.add(builder.or(predicatesLanguage.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.getWarehouse() != null && searchCompanyForm.getWarehouse() == 3) {
				final Subquery<Long> subqueryAddress = criteriaQuery.subquery(Long.class);
				final Root<Company> subrootAddress = subqueryAddress.from(Company.class);
				final Join<Company, CompanyAddress> subjoinAddress = subrootAddress.join("addresses");
				subqueryAddress.select(builder.count(subjoinAddress)).where(builder.equal(subrootAddress.get("id"), rootCompany.get("id")));
				final Expression<Long> countAddress = subqueryAddress.getSelection();
				predicates.add(builder.greaterThan(countAddress, 1L));
			}
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				searchCompanyForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchCompanyForm.getUserId()) 
						: builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		final Order order;
		switch (searchCompanyForm.getSort()) {
		case 1: order = searchCompanyForm.isDesc() ? builder.desc(rootAccount.get("modifiedDate")) : builder.asc(rootAccount.get("modifiedDate")); break;
		case 2: order = searchCompanyForm.isDesc() ? builder.desc(rootCompany.get("buildDate")) : builder.asc(rootCompany.get("buildDate")); break;
		case 3: order = searchCompanyForm.isDesc() ? builder.desc(rootAccount.get("numberOfVisits")) : builder.asc(rootAccount.get("numberOfVisits")); break;
		default: order = searchCompanyForm.isDesc() ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename"));
		}
		criteriaQuery.select(builder.construct(CompanyWidgetMini.class, rootCompany, rootSeo.get("url"), favorite, premium, 
				rootAccount.get("modifiedDate"), rootCompany.get("buildDate"), rootAccount.get("numberOfVisits"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<CompanyWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchCompanyForm.getPage() - 1) * searchCompanyForm.getRow()).setMaxResults(searchCompanyForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<Long> findAllCompanyEasylist(final SearchCompanyForm searchCompanyForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootSeo.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchCompanyForm.getToken())) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + searchCompanyForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchCompanyForm.getKeysword())) {
			final String[] keysword = searchCompanyForm.parseKeysword();
			final List<Predicate> predicatesSeo = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootSeo.get("keysword"));
				predicatesSeo.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesSeo.toArray(new Predicate[0])));
		}
		if(searchCompanyForm.hasPresentFilter()) {
			if(searchCompanyForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchCompanyForm.parseSectors()));
			}
			if(searchCompanyForm.hasPresentCompanyAddress()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				if(!StringUtils.isEmpty(searchCompanyForm.getPostal())) {
					predicates.add(searchCompanyForm.isEqualPostal() 
							? builder.equal(joinAddress.get("postal"), searchCompanyForm.getPostal()) 
									: builder.notEqual(joinAddress.get("postal"), searchCompanyForm.getPostal()));
				} else {
					predicates.add(builder.in(joinAddress.get("wilaya")).value(searchCompanyForm.parseWilayas()));
				}
			}
			if(!StringUtils.isEmpty(searchCompanyForm.getDateBegin())) {
				switch(searchCompanyForm.getIndexDate()) {
				case 1: predicates.add(builder.lessThan(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 2: predicates.add(builder.greaterThan(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin())); break;
				case 3: predicates.add(builder.between(rootCompany.get("buildDate"), searchCompanyForm.parseDateBegin(), searchCompanyForm.parseDateEnd()));
				}
			}
			if(searchCompanyForm.hasPresentCompanyBriefcase()) {
				final Root<CompanyBriefcase> rootBriefcase = criteriaQuery.from(CompanyBriefcase.class);
				predicates.add(builder.equal(rootBriefcase.get("companyId"), rootCompany.get("id")));
				if(searchCompanyForm.hasPresentTypes()) {
					predicates.add(builder.in(rootBriefcase.get("type")).value(searchCompanyForm.parseTypes()));
				}
				if(searchCompanyForm.hasPresentBriefcases()) {
					predicates.add(builder.in(rootBriefcase.get("briefcase")).value(searchCompanyForm.parseBriefcases()));
				}
				if(searchCompanyForm.getWarehouse() != null) {
					switch(searchCompanyForm.getWarehouse()) {
					case 1: predicates.add(builder.isFalse(rootBriefcase.get("warehouse"))); break;
					case 2: predicates.add(builder.isTrue(rootBriefcase.get("warehouse")));
					}
				}
				if(!StringUtils.isEmpty(searchCompanyForm.getCapital())) {
					switch(searchCompanyForm.getIndexCapital()) {
					case 1: predicates.add(builder.greaterThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 2: predicates.add(builder.lessThan(rootBriefcase.get("capital"), searchCompanyForm.parseCapital())); break;
					case 3: predicates.add(builder.equal(rootBriefcase.get("capital"), searchCompanyForm.parseCapital()));
					}
				}
			}
			if(searchCompanyForm.hasPresentCredits()) {
				final Root<CompanyCredit> rootCredit = criteriaQuery.from(CompanyCredit.class);
				final List<Predicate> predicatesCredit = new ArrayList<>();
				for(int i = 0; i < 5; i++) {
					if(searchCompanyForm.getCredits()[i]) {
						switch(i + 1) {
						case 1: predicatesCredit.add(builder.isTrue(rootCredit.get("cheque"))); break;
						case 2: predicatesCredit.add(builder.isTrue(rootCredit.get("versement"))); break;
						case 3: predicatesCredit.add(builder.isTrue(rootCredit.get("espece"))); break;
						case 4: predicatesCredit.add(builder.isTrue(rootCredit.get("carte"))); break;
						default: predicatesCredit.add(builder.isTrue(rootCredit.get("paypal")));
						}
					}
				}
				predicates.add(builder.equal(rootCredit.get("companyId"), rootCompany.get("id")));
				predicates.add(builder.or(predicatesCredit.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.hasPresentDigitals()) {
				if(searchCompanyForm.hasPresentSocials()) {
					final Root<CompanyLinked> rootLinked = criteriaQuery.from(CompanyLinked.class);
					final List<Predicate> predicatesLinked = new ArrayList<>();
					predicatesLinked.add(builder.isNotNull(rootLinked.get("facebook")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("twitter")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("linkedin")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("youtube")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("google")));
					predicatesLinked.add(builder.isNotNull(rootLinked.get("instagram")));
					predicates.add(builder.equal(rootLinked.get("companyId"), rootCompany.get("id")));
					predicates.add(builder.or(predicatesLinked.toArray(new Predicate[0])));
				}
				if(searchCompanyForm.hasPresentWebsites()) {
					final Subquery<Long> subqueryLinked = criteriaQuery.subquery(Long.class);
					final Root<CompanyLinked> subrootLinked = subqueryLinked.from(CompanyLinked.class);
					final Join<CompanyLinked, LinkedWebsite> subjoinWebiste = subrootLinked.join("websites");
					subqueryLinked.select(builder.count(subjoinWebiste)).where(builder.equal(subrootLinked.get("companyId"), rootCompany.get("id")));
					final Expression<Long> countWebsite = subqueryLinked.getSelection();
					predicates.add(builder.greaterThan(countWebsite, 0L));
				}
				if(searchCompanyForm.hasPresentCarte()) {
					final Root<CompanyLocation> rootLocation = criteriaQuery.from(CompanyLocation.class);
					predicates.add(builder.equal(rootLocation.get("companyId"), rootCompany.get("id")));
				}
			}
			if(searchCompanyForm.hasPresentContacts()) {
				final Root<CompanyShedule> rootShedule = criteriaQuery.from(CompanyShedule.class);
				predicates.add(builder.equal(rootShedule.get("companyId"), rootCompany.get("id")));
				if(searchCompanyForm.getContacts()[2]) {
					predicates.add(builder.isNotNull(rootShedule.get("mobile")));
				}
				if(searchCompanyForm.getContacts()[3]) {
					predicates.add(builder.isNotNull(rootShedule.get("fax")));
				}
			}
			if(searchCompanyForm.hasPresentLanguages()) {
				final List<Predicate> predicatesLanguage = new ArrayList<>();
				if(searchCompanyForm.getLanguages()[0]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "fr"));
				}
				if(searchCompanyForm.getLanguages()[1]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "en"));
				}
				if(searchCompanyForm.getLanguages()[2]) {
					predicatesLanguage.add(builder.equal(rootCompany.get("lang"), "ar"));
				}
				predicates.add(builder.or(predicatesLanguage.toArray(new Predicate[0])));
			}
			if(searchCompanyForm.getWarehouse() != null && searchCompanyForm.getWarehouse() == 3) {
				final Subquery<Long> subqueryAddress = criteriaQuery.subquery(Long.class);
				final Root<Company> subrootAddress = subqueryAddress.from(Company.class);
				final Join<Company, CompanyAddress> subjoinAddress = subrootAddress.join("addresses");
				subqueryAddress.select(builder.count(subjoinAddress)).where(builder.equal(subrootAddress.get("id"), rootCompany.get("id")));
				final Expression<Long> countAddress = subqueryAddress.getSelection();
				predicates.add(builder.greaterThan(countAddress, 1L));
			}
		}
		criteriaQuery.select(rootCompany.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public Long countAllCompanyWidgetB2C(final SearchCompanyQuickly searchCompanyQuickly) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<WidgetB2C> rootB2C = criteriaQuery.from(WidgetB2C.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootB2C.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootB2C.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchCompanyQuickly.getToken())) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + searchCompanyQuickly.getToken().toLowerCase() + "%"));
		}
		if(searchCompanyQuickly.hasPresentFilter()) {
			if(searchCompanyQuickly.hasPresentWilaya()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.equal(joinAddress.get("wilaya"), searchCompanyQuickly.getWilaya()));
			}
			if(searchCompanyQuickly.hasPresentCategory()) {
				predicates.add(builder.equal(rootB2C.get("category"), searchCompanyQuickly.getCategory()));
			}
			if(searchCompanyQuickly.hasPresentActivity()) {
				predicates.add(builder.equal(rootB2C.get("activity"), searchCompanyQuickly.getActivity()));
			}
			if(searchCompanyQuickly.isFiltred()) {
				predicates.add(builder.isTrue(rootB2C.get("filtred")));
			}
		}
		criteriaQuery.select(builder.countDistinct(rootCompany)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<CompanyWidgetB2C> findCompanyWidgetB2CList(final SearchCompanyQuickly searchCompanyQuickly) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetB2C> criteriaQuery = builder.createQuery(CompanyWidgetB2C.class);
		final Root<WidgetB2C> rootB2C = criteriaQuery.from(WidgetB2C.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Subquery<Double> subqueryNote = criteriaQuery.subquery(Double.class);
		final Root<Evaluation> subrootNote = subqueryNote.from(Evaluation.class);
		final Subquery<Long> subqueryEvaluation = criteriaQuery.subquery(Long.class);
		final Root<Evaluation> subrootEvaluation = subqueryEvaluation.from(Evaluation.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<Evaluation> subrootLiked = subqueryLiked.from(Evaluation.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootB2C.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.isTrue(rootB2C.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchCompanyQuickly.getToken())) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + searchCompanyQuickly.getToken().toLowerCase() + "%"));
		}
		if(searchCompanyQuickly.hasPresentFilter()) {
			if(searchCompanyQuickly.hasPresentWilaya()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.equal(joinAddress.get("wilaya"), searchCompanyQuickly.getWilaya()));
			}
			if(searchCompanyQuickly.hasPresentCategory()) {
				predicates.add(builder.equal(rootB2C.get("category"), searchCompanyQuickly.getCategory()));
			}
			if(searchCompanyQuickly.hasPresentActivity()) {
				predicates.add(builder.equal(rootB2C.get("activity"), searchCompanyQuickly.getActivity()));
			}
			if(searchCompanyQuickly.isFiltred()) {
				predicates.add(builder.isTrue(rootB2C.get("filtred")));
			}
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryNote.select(builder.avg(subrootNote.get("note"))).where(builder.equal(subrootNote.get("companyId"), rootCompany.get("id")));
		subqueryEvaluation.select(builder.count(subrootEvaluation)).where(builder.equal(subrootEvaluation.get("companyId"), rootCompany.get("id")));
		subqueryLiked.select(builder.count(subrootLiked)).where(builder.and(builder.equal(subrootLiked.get("companyId"), rootCompany.get("id")), 
				builder.isTrue(subrootLiked.get("liked"))));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				searchCompanyQuickly.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchCompanyQuickly.getUserId()) 
						: builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Double> note = subqueryNote.getSelection();
		final Expression<Long> evaluation = subqueryEvaluation.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		final Order order;
		switch (searchCompanyQuickly.getSort()) {
		case 1: order = searchCompanyQuickly.isDesc() ? builder.desc(rootAccount.get("modifiedDate")) : builder.asc(rootAccount.get("modifiedDate")); break;
		case 2: order = searchCompanyQuickly.isDesc() ? builder.desc(rootCompany.get("buildDate")) : builder.asc(rootCompany.get("buildDate")); break;
		case 3: order = searchCompanyQuickly.isDesc() ? builder.desc(rootAccount.get("numberOfVisits")) : builder.asc(rootAccount.get("numberOfVisits")); break;
		default: order = searchCompanyQuickly.isDesc() ? builder.desc(rootCompany.get("tradename")) : builder.asc(rootCompany.get("tradename"));
		}
		criteriaQuery.select(builder.construct(CompanyWidgetB2C.class, rootB2C, rootCompany, rootSeo.get("url"), note, evaluation, liked, favorite, premium, 
				rootAccount.get("modifiedDate"), rootCompany.get("buildDate"), rootAccount.get("numberOfVisits"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<CompanyWidgetB2C> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchCompanyQuickly.getPage() - 1) * searchCompanyQuickly.getRow()).setMaxResults(searchCompanyQuickly.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<CompanyWidgetMini> findCompanyWidgetListWithToken(final Long userId, final String search, final Integer wilaya, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetMini> criteriaQuery = builder.createQuery(CompanyWidgetMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> tradename = builder.lower(rootCompany.get("tradename"));
			predicates.add(builder.like(tradename, "%" + search.toLowerCase() + "%"));
		}
		if(wilaya != null && wilaya != 0) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				userId != null ? builder.equal(subrootFavorite.get("userId"), userId) : builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(CompanyWidgetMini.class, rootCompany, rootSeo.get("url"), favorite, premium, 
				rootSearch.get("token"), rootSearch.get("completed"), rootAccount.get("modifiedDate"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootSearch.get("token")), builder.desc(rootSearch.get("completed")), 
				builder.desc(rootAccount.get("modifiedDate")), builder.asc(rootCompany.get("tradename")));
		final TypedQuery<CompanyWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<CompanyWidgetMini> findCompanyWidgetListWithAgent(final Long userId, final String search, final Integer wilaya, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetMini> criteriaQuery = builder.createQuery(CompanyWidgetMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Root<Agent> rootAgent = criteriaQuery.from(Agent.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAgent.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			final String[] tags = search.split(" ");
			final Expression<String> exp = builder.concat(rootAgent.get("firstName"), builder.concat(" ", rootAgent.get("lastName")));
			final Expression<String> agentname = builder.lower(exp);
			final List<Predicate> predicatesTag = new ArrayList<>();
			for (final String tag : tags) {
				predicatesTag.add(builder.like(agentname, "%" + tag.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesTag.toArray(new Predicate[0])));
		}
		if(wilaya != null && wilaya != 0) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				userId != null ? builder.equal(subrootFavorite.get("userId"), userId) : builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(CompanyWidgetMini.class, rootCompany, rootSeo.get("url"), favorite, premium, 
				rootSearch.get("filter"), rootSearch.get("completed"), rootAccount.get("modifiedDate"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootSearch.get("filter")), builder.desc(rootSearch.get("completed")), 
				builder.desc(rootAccount.get("modifiedDate")), builder.asc(rootCompany.get("tradename")));
		final TypedQuery<CompanyWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<CompanyWidgetMini> findCompanyWidgetListWithKeyword(final Long userId, final String search, final Integer wilaya, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyWidgetMini> criteriaQuery = builder.createQuery(CompanyWidgetMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Subquery<Integer> subqueryFavorite = criteriaQuery.subquery(Integer.class);
		final Root<FavoriteCompany> subrootFavorite = subqueryFavorite.from(FavoriteCompany.class);
		final Subquery<Integer> subqueryPremium = criteriaQuery.subquery(Integer.class);
		final Root<Premium> subrootPremium = subqueryPremium.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesPremium = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(search)) {
			final String[] keysword = search.split(" ");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootSeo.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(wilaya != null && wilaya != 0) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicates.add(builder.equal(joinAddress.get("wilaya"), wilaya));
		}
		predicatesPremium.add(builder.equal(subrootPremium.get("companyId"), rootCompany.get("id")));
		predicatesPremium.add(builder.greaterThan(subrootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicatesPremium.add(builder.isTrue(subrootPremium.get("enabled")));
		subqueryFavorite.select(subrootFavorite.get("type")).where(builder.and(builder.equal(subrootFavorite.get("companyId"), rootCompany.get("id")), 
				userId != null ? builder.equal(subrootFavorite.get("userId"), userId) : builder.isNull(subrootFavorite.get("userId"))));
		subqueryPremium.select(subrootPremium.get("pass")).where(predicatesPremium.toArray(new Predicate[0]));
		final Expression<Integer> favorite = subqueryFavorite.getSelection();
		final Expression<Integer> premium = subqueryPremium.getSelection();
		criteriaQuery.select(builder.construct(CompanyWidgetMini.class, rootCompany, rootSeo.get("url"), favorite, premium, 
				rootSearch.get("tag"), rootSearch.get("completed"), rootAccount.get("modifiedDate"), rootCompany.get("tradename"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootSearch.get("tag")), builder.desc(rootSearch.get("completed")), 
				builder.desc(rootAccount.get("modifiedDate")), builder.asc(rootCompany.get("tradename")));
		final TypedQuery<CompanyWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<CompanyLogoMini> findLastCompanyLogoMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyLogoMini> criteriaQuery = builder.createQuery(CompanyLogoMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.isTrue(rootCompany.get("hasAvatar")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(CompanyLogoMini.class, rootCompany.get("id"), rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootSearch.get("completed")), builder.desc(rootAccount.get("numberOfVisits")));
		final TypedQuery<CompanyLogoMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<CompanyLogoMini> findLastCompanyPremiumMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<CompanyLogoMini> criteriaQuery = builder.createQuery(CompanyLogoMini.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Root<CompanyAccount> rootAccount = criteriaQuery.from(CompanyAccount.class);
		final Root<CompanySearch> rootSearch = criteriaQuery.from(CompanySearch.class);
		final Root<Premium> rootPremium = criteriaQuery.from(Premium.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootAccount.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSearch.get("companyId")));
		predicates.add(builder.equal(rootCompany.get("id"), rootPremium.get("companyId")));
		predicates.add(builder.greaterThan(rootPremium.get("expiryDate"), new DateTime(Date.from(Instant.now()))));
		predicates.add(builder.isTrue(rootPremium.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("hasAvatar")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(CompanyLogoMini.class, rootCompany.get("id"), rootCompany.get("tradename"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootPremium.get("pass")), builder.desc(rootSearch.get("completed")));
		final TypedQuery<CompanyLogoMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
}
