package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityLike;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.result.ActualityMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.portfolio.ActualityLine;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetActuality;
import com.rinitec.algerieoffice.web.modal.mapsite.ActualityMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.ScreenInboxNews;

@Repository
public class ActualityRepositoryImpl implements ActualityRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllActualityCriteria(final Long companyId, final Long filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(filter != null) {
			predicates.add(builder.equal(root.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ActualityLine> findAllActualityCriteria(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ActualityLine> criteriaQuery = builder.createQuery(ActualityLine.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLike = subqueryLike.from(ActualityLike.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<ActualityComment> subrootComment = subqueryComment.from(ActualityComment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("companyId"), companyId));
		predicates.add(builder.equal(rootActuality.get("autorId"), rootUser.get("id")));
		final Expression<String> exp = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(filter != null) {
			predicates.add(builder.equal(rootActuality.get("autorId"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootActuality.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("actualityId"), rootActuality.get("id")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("actualityId"), rootActuality.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> countComment = subqueryComment.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootActuality.get("actuDate")) : builder.asc(rootActuality.get("actuDate")); break;
		case 2: order = hasDesc ? builder.desc(rootActuality.get("title")) : builder.asc(rootActuality.get("title")); break;
		case 3: order = hasDesc ? builder.desc(exp) : builder.asc(exp); break;
		default: order = hasDesc ? builder.desc(rootActuality.get("modifiedDate")) : builder.asc(rootActuality.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(ActualityLine.class, rootActuality, exp, countLike, countComment));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ActualityLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<UserMini> findAllAutorCriteria(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UserMini> criteriaQuery = builder.createQuery(UserMini.class);
		final Root<User> root = criteriaQuery.from(User.class);
		final Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		final Root<Actuality> subroot = subquery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		final Expression<String> exp = builder.concat(root.get("firstName"), builder.concat(" ", root.get("lastName")));
		predicates.add(builder.equal(subroot.get("companyId"), companyId));
		subquery.select(subroot.get("autorId")).distinct(true).where(predicates.toArray(new Predicate[0]));
		criteriaQuery.select(builder.construct(UserMini.class, root.get("id"), exp));
		criteriaQuery.where(builder.in(root.get("id")).value(subquery)).orderBy(builder.asc(exp));
		final TypedQuery<UserMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerActualityCriteria(final Long companyId, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ExplorerWidgetActuality> findAllExplorerActualityCriteria(final Long userId, final Long companyId, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ExplorerWidgetActuality> criteriaQuery = builder.createQuery(ExplorerWidgetActuality.class);
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLike = subqueryLike.from(ActualityLike.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<ActualityComment> subrootComment = subqueryComment.from(ActualityComment.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLiked = subqueryLiked.from(ActualityLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesLiked = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		predicatesLiked.add(builder.equal(subrootLiked.get("actualityId"), root.get("id")));
		predicatesLiked.add(userId != null ? builder.equal(subrootLiked.get("userId"), userId) : builder.isNull(subrootLiked.get("userId")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("actualityId"), root.get("id")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("actualityId"), root.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(predicatesLiked.toArray(new Predicate[0]));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> countComment = subqueryComment.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(root.get("actuDate")) : builder.asc(root.get("actuDate")); break;
		default: order = hasDesc ? builder.desc(root.get("modifiedDate")) : builder.asc(root.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(ExplorerWidgetActuality.class, root, countLike, countComment, liked));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<ExplorerWidgetActuality> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<ActualityMini> findLastExplorerActuality(final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ActualityMini> criteriaQuery = builder.createQuery(ActualityMini.class);
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.construct(ActualityMini.class, root.get("title"), root.get("actuDate"), root.get("photoUUID")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("actuDate")));
		final TypedQuery<ActualityMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllNewsWidget(final SearchNewsForm searchNewsForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(searchNewsForm.hasPresentFilter()) {
			if(!StringUtils.isEmpty(searchNewsForm.getToken())) {
				final Expression<String> title = builder.lower(rootActuality.get("title"));
				predicates.add(builder.like(title, "%" + searchNewsForm.getToken().toLowerCase() + "%"));
			}
			if(searchNewsForm.hasPresentSector()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.equal(joinActivity.get("sector"), searchNewsForm.getSector()));
			}
			if(searchNewsForm.hasPresentWilaya()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.equal(joinAddress.get("wilaya"), searchNewsForm.getWilaya()));
			}
		}
		criteriaQuery.select(builder.countDistinct(rootActuality)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NewsWidgetMini> findNewsWidgetList(final SearchNewsForm searchNewsForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsWidgetMini> criteriaQuery = builder.createQuery(NewsWidgetMini.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLike = subqueryLike.from(ActualityLike.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<ActualityComment> subrootComment = subqueryComment.from(ActualityComment.class);
		final Subquery<Long> subqueryLiked = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLiked = subqueryLiked.from(ActualityLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesLiked = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(searchNewsForm.hasPresentFilter()) {
			if(!StringUtils.isEmpty(searchNewsForm.getToken())) {
				final Expression<String> title = builder.lower(rootActuality.get("title"));
				predicates.add(builder.like(title, "%" + searchNewsForm.getToken().toLowerCase() + "%"));
			}
			if(searchNewsForm.hasPresentSector()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.equal(joinActivity.get("sector"), searchNewsForm.getSector()));
			}
			if(searchNewsForm.hasPresentWilaya()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.equal(joinAddress.get("wilaya"), searchNewsForm.getWilaya()));
			}
		}
		predicatesLiked.add(builder.equal(subrootLiked.get("actualityId"), rootActuality.get("id")));
		predicatesLiked.add(searchNewsForm.getUserId() != null ? builder.equal(subrootLiked.get("userId"), searchNewsForm.getUserId()) 
				: builder.isNull(subrootLiked.get("userId")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("actualityId"), rootActuality.get("id")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("actualityId"), rootActuality.get("id")));
		subqueryLiked.select(subrootLiked.get("userId")).where(predicatesLiked.toArray(new Predicate[0]));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> countComment = subqueryComment.getSelection();
		final Expression<Long> liked = subqueryLiked.getSelection();
		criteriaQuery.select(builder.construct(NewsWidgetMini.class, rootActuality, rootCompany, rootSeo.get("url"), countLike, countComment, liked, 
				rootActuality.get("sharedDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootActuality.get("sharedDate")));
		final TypedQuery<NewsWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchNewsForm.getPage() - 1) * searchNewsForm.getRow()).setMaxResults(searchNewsForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public ScreenInboxNews findOneScreenInboxNews(final UUID actuId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ScreenInboxNews> criteriaQuery = builder.createQuery(ScreenInboxNews.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<ActualityLike> subrootLike = subqueryLike.from(ActualityLike.class);
		final Subquery<Long> subqueryComment = criteriaQuery.subquery(Long.class);
		final Root<ActualityComment> subrootComment = subqueryComment.from(ActualityComment.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("id"), actuId));
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("actualityId"), rootActuality.get("id")));
		subqueryComment.select(builder.count(subrootComment)).where(builder.equal(subrootComment.get("actualityId"), rootActuality.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Expression<Long> countComment = subqueryComment.getSelection();
		criteriaQuery.select(builder.construct(ScreenInboxNews.class, rootActuality, rootCompany, rootSeo.get("url"), countLike, countComment));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		final TypedQuery<ScreenInboxNews> query = entityManager.createQuery(criteriaQuery);
		return query.getSingleResult();
	}
	
	@Override
	public Long countAllActyalityMapsite() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootActuality)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ActualityMapsite> findAllActualityMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<ActualityMapsite> criteriaQuery = builder.createQuery(ActualityMapsite.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(ActualityMapsite.class, rootActuality.get("id"), rootActuality.get("modifiedDate")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootActuality.get("modifiedDate")));
		final TypedQuery<ActualityMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllActuality(final Integer filter, final boolean published) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Actuality> rootActuality = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(rootActuality.get("modifiedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(published) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(rootActuality.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.isTrue(rootActuality.get("hasPublished")));
			predicates.add(builder.isTrue(rootCompany.get("enabled")));
			predicates.add(builder.isTrue(rootCompany.get("active")));
			predicates.add(builder.isFalse(rootCompany.get("locked")));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootActuality));
		} else {
			criteriaQuery.select(builder.count(rootActuality)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<UUIDMini> findAllActualityNewsletterMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
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
		final Root<Actuality> root = criteriaQuery.from(Actuality.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		predicates.add(builder.in(root.get("id")).value(lines));
		criteriaQuery.select(builder.construct(NewsletterItem.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("modifiedDate")));
		final TypedQuery<NewsletterItem> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
