package com.rinitec.algerieoffice.persistence.dao.company.posts;

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

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.posts.PostLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePostLine;
import com.rinitec.algerieoffice.web.modal.mapsite.PostMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostSimilarScreen;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostWidgetMini;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final UUID parseFilterUUID(final String filter) {
		try {
			return UUID.fromString(filter);
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	public Long countAllPostCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(root.get("categoryId")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(root.get("categoryId"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PostLine> findAllPostCriteria(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostLine> criteriaQuery = builder.createQuery(PostLine.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<Category> subroot = subquery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.equal(rootPost.get("autorId"), rootUser.get("id")));
		final Expression<String> username = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(rootPost.get("categoryId")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(rootPost.get("categoryId"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(subroot.get("name")).where(builder.equal(subroot.get("id"), rootPost.get("categoryId")));
		final Expression<String> categoryname = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title")); break;
		case 2: order = hasDesc ? builder.desc(username) : builder.asc(username); break;
		default: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(PostLine.class, rootPost, username, categoryname));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PostLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<PostMini> findAllPublishedPost(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostMini> criteriaQuery = builder.createQuery(PostMini.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		criteriaQuery.select(builder.construct(PostMini.class, root.get("title"), root.get("identify")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(root.get("title")));
		final TypedQuery<PostMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
	public Long countAllExplorerPostCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(!StringUtils.isEmpty(filter)) {
			final UUID filterUUID = parseFilterUUID(filter);
			if(filterUUID != null) {
				final Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
				final Root<Category> subroot = subquery.from(Category.class);
				final List<Predicate> subpredicates = new ArrayList<>();
				subpredicates.add(builder.or(builder.equal(subroot.get("id"), filterUUID), builder.equal(subroot.get("parentUUID"), filterUUID)));
				subquery.select(subroot.get("id")).distinct(true).where(subpredicates.toArray(new Predicate[0]));
				predicates.add(builder.in(root.get("categoryId")).value(subquery));
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<Object[]> findAllExplorerPostCriteria(final Long companyId, final String filter, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
		final Root<PostPhoto> rootPostPhoto = criteriaQuery.from(PostPhoto.class);
		final Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
		final Root<Category> subroot = subquery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostPhoto.get("postUUID"), rootPost.get("id")));
		predicates.add(builder.isTrue(rootPostPhoto.get("hasPrincipal")));
		if(!StringUtils.isEmpty(filter)) {
			final UUID filterUUID = parseFilterUUID(filter);
			if(filterUUID != null) {
				final Subquery<UUID> subqueryFilter = criteriaQuery.subquery(UUID.class);
				final Root<Category> subrootFilter = subqueryFilter.from(Category.class);
				final List<Predicate> predicatesFilter = new ArrayList<>();
				predicatesFilter.add(builder.or(builder.equal(subrootFilter.get("id"), filterUUID), 
						builder.equal(subrootFilter.get("parentUUID"), filterUUID)));
				subqueryFilter.select(subrootFilter.get("id")).distinct(true).where(predicatesFilter.toArray(new Predicate[0]));
				predicates.add(builder.in(rootPost.get("categoryId")).value(subqueryFilter));
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(subroot).where(builder.equal(subroot.get("id"), rootPost.get("categoryId")));
		final Expression<Category> category = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPost.get("createdDate")) : builder.asc(rootPost.get("createdDate")); break;
		case 2: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootPostDetail.get("priceValue")) : builder.asc(rootPostDetail.get("priceValue"));
		}
		criteriaQuery.multiselect(rootPost, rootPostDetail, rootPostPhoto, category).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllPostWidget(final SearchPostForm searchPostForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchPostForm.getToken())) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + searchPostForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchPostForm.getKeysword())) {
			final String[] keysword = searchPostForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootPost.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchPostForm.hasPresentFilter()) {
			if(searchPostForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchPostForm.parseSectors()));
			}
			if(searchPostForm.hasPresentWilayas()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.in(joinAddress.get("wilaya")).value(searchPostForm.parseWilayas()));
			}
			if(searchPostForm.getType() != 1) {
				switch(searchPostForm.getType()) {
				case 2: predicates.add(builder.isFalse(rootPost.get("service"))); break;
				case 3: predicates.add(builder.isTrue(rootPost.get("service")));
				}
			}
			if(searchPostForm.hasPresentDetail()) {
				final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
				predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
				if(searchPostForm.getPriceType() != null) {
					predicates.add(builder.equal(rootPostDetail.get("priceType"), searchPostForm.getPriceType()));
				}
				if(searchPostForm.hasPresentValue()) {
					switch(searchPostForm.getIndexValue()) {
					case 1: predicates.add(builder.lessThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
					case 2: predicates.add(builder.greaterThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
					case 3: predicates.add(builder.between(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin(), searchPostForm.parseValueEnd()));
					}
				}
				if(searchPostForm.hasPresentMores()) {
					if(searchPostForm.hasPresentParrain()) {
						predicates.add(builder.isNotNull(rootPostDetail.get("priceParrain")));
					}
					if(searchPostForm.hasPresentPrecision()) {
						predicates.add(builder.isNotNull(rootPostDetail.get("pricePrecision")));
					}
				}
				if(searchPostForm.hasPresentURL()) {
					predicates.add(builder.isNotNull(rootPostDetail.get("urlExtern")));
				}
				if(searchPostForm.getState() != 1) {
					switch(searchPostForm.getState()) {
					case 2: predicates.add(builder.isTrue(rootPostDetail.get("labelNew"))); break;
					case 3: predicates.add(builder.isTrue(rootPostDetail.get("labelExclusif")));
					}
				}
			}
		}
		criteriaQuery.select(builder.countDistinct(rootPost)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PostWidgetMini> findPostWidgetList(final SearchPostForm searchPostForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostWidgetMini> criteriaQuery = builder.createQuery(PostWidgetMini.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
		final Root<PostSearch> rootPostSearch = criteriaQuery.from(PostSearch.class);
		final Root<PostPhoto> rootPostPhoto = criteriaQuery.from(PostPhoto.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final Subquery<Long> subqueryFavorite = criteriaQuery.subquery(Long.class);
		final Root<FavoriteDocument> subrootFavorite = subqueryFavorite.from(FavoriteDocument.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesFavorite = new ArrayList<>();
		predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostSearch.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostPhoto.get("postUUID"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootPostPhoto.get("hasPrincipal")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchPostForm.getToken())) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + searchPostForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchPostForm.getKeysword())) {
			final String[] keysword = searchPostForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootPost.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchPostForm.hasPresentFilter()) {
			if(searchPostForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchPostForm.parseSectors()));
			}
			if(searchPostForm.hasPresentWilayas()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.in(joinAddress.get("wilaya")).value(searchPostForm.parseWilayas()));
			}
			if(searchPostForm.getType() != 1) {
				switch(searchPostForm.getType()) {
				case 2: predicates.add(builder.isFalse(rootPost.get("service"))); break;
				case 3: predicates.add(builder.isTrue(rootPost.get("service")));
				}
			}
			if(searchPostForm.getPriceType() != null) {
				predicates.add(builder.equal(rootPostDetail.get("priceType"), searchPostForm.getPriceType()));
			}
			if(searchPostForm.hasPresentValue()) {
				switch(searchPostForm.getIndexValue()) {
				case 1: predicates.add(builder.lessThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
				case 2: predicates.add(builder.greaterThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
				case 3: predicates.add(builder.between(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin(), searchPostForm.parseValueEnd()));
				}
			}
			if(searchPostForm.hasPresentMores()) {
				if(searchPostForm.hasPresentParrain()) {
					predicates.add(builder.isNotNull(rootPostDetail.get("priceParrain")));
				}
				if(searchPostForm.hasPresentPrecision()) {
					predicates.add(builder.isNotNull(rootPostDetail.get("pricePrecision")));
				}
			}
			if(searchPostForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootPostDetail.get("urlExtern")));
			}
			if(searchPostForm.getState() != 1) {
				switch(searchPostForm.getState()) {
				case 2: predicates.add(builder.isTrue(rootPostDetail.get("labelNew"))); break;
				case 3: predicates.add(builder.isTrue(rootPostDetail.get("labelExclusif")));
				}
			}
		}
		predicatesFavorite.add(builder.equal(subrootFavorite.get("documentId"), rootPost.get("id")));
		predicatesFavorite.add(builder.equal(subrootFavorite.get("type"), DocumentType.post));
		predicatesFavorite.add(searchPostForm.getUserId() != null ? builder.equal(subrootFavorite.get("userId"), searchPostForm.getUserId()) 
				: builder.isNull(subrootFavorite.get("userId")));
		subqueryFavorite.select(subrootFavorite.get("userId")).where(predicatesFavorite.toArray(new Predicate[0]));
		final Expression<Long> favorite = subqueryFavorite.getSelection();
		final Order order;
		switch (searchPostForm.getSort()) {
		case 1: order = searchPostForm.isDesc() ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate")); break;
		case 2: order = searchPostForm.isDesc() ? builder.desc(rootPost.get("createdDate")) : builder.asc(rootPost.get("createdDate")); break;
		case 3: order = searchPostForm.isDesc() ? builder.desc(rootPostSearch.get("clickCount")) : builder.asc(rootPostSearch.get("clickCount")); break;
		default: order = searchPostForm.isDesc() ? builder.desc(rootPostDetail.get("priceValue")) : builder.asc(rootPostDetail.get("priceValue"));
		}
		criteriaQuery.select(builder.construct(PostWidgetMini.class, rootPost, rootPostDetail, rootPostPhoto, rootCompany, rootSeo.get("url"), favorite, 
				rootPost.get("modifiedDate"), rootPost.get("createdDate"), rootPostSearch.get("clickCount"), rootPostDetail.get("priceValue"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<PostWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((searchPostForm.getPage() - 1) * searchPostForm.getRow()).setMaxResults(searchPostForm.getRow());
		return query.getResultList();
	}
	
	@Override
	public List<UUID> findAllPostEasylist(final SearchPostForm searchPostForm) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUID> criteriaQuery = builder.createQuery(UUID.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		if(!StringUtils.isEmpty(searchPostForm.getToken())) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + searchPostForm.getToken().toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(searchPostForm.getKeysword())) {
			final String[] keysword = searchPostForm.parseKeysword();
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootPost.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicates.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(searchPostForm.hasPresentFilter()) {
			if(searchPostForm.hasPresentSectors()) {
				final Join<Company, Activity> joinActivity = rootCompany.join("activities");
				predicates.add(builder.in(joinActivity.get("sector")).value(searchPostForm.parseSectors()));
			}
			if(searchPostForm.hasPresentWilayas()) {
				final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
				predicates.add(builder.in(joinAddress.get("wilaya")).value(searchPostForm.parseWilayas()));
			}
			if(searchPostForm.getType() != 1) {
				switch(searchPostForm.getType()) {
				case 2: predicates.add(builder.isFalse(rootPost.get("service"))); break;
				case 3: predicates.add(builder.isTrue(rootPost.get("service")));
				}
			}
			if(searchPostForm.getPriceType() != null) {
				predicates.add(builder.equal(rootPostDetail.get("priceType"), searchPostForm.getPriceType()));
			}
			if(searchPostForm.hasPresentValue()) {
				switch(searchPostForm.getIndexValue()) {
				case 1: predicates.add(builder.lessThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
				case 2: predicates.add(builder.greaterThan(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin())); break;
				case 3: predicates.add(builder.between(rootPostDetail.get("priceValue"), searchPostForm.parseValueBegin(), searchPostForm.parseValueEnd()));
				}
			}
			if(searchPostForm.hasPresentMores()) {
				if(searchPostForm.hasPresentParrain()) {
					predicates.add(builder.isNotNull(rootPostDetail.get("priceParrain")));
				}
				if(searchPostForm.hasPresentPrecision()) {
					predicates.add(builder.isNotNull(rootPostDetail.get("pricePrecision")));
				}
			}
			if(searchPostForm.hasPresentURL()) {
				predicates.add(builder.isNotNull(rootPostDetail.get("urlExtern")));
			}
			if(searchPostForm.getState() != 1) {
				switch(searchPostForm.getState()) {
				case 2: predicates.add(builder.isTrue(rootPostDetail.get("labelNew"))); break;
				case 3: predicates.add(builder.isTrue(rootPostDetail.get("labelExclusif")));
				}
			}
		}
		criteriaQuery.select(rootPost.get("id")).distinct(true).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	@Override
	public List<PostSimilarScreen> findPostProxisList(final Post post, final List<Integer> sectors, final List<Integer> wilayas, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostSimilarScreen> criteriaQuery = builder.createQuery(PostSimilarScreen.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
		final Root<PostSearch> rootPostSearch = criteriaQuery.from(PostSearch.class);
		final Root<PostPhoto> rootPostPhoto = criteriaQuery.from(PostPhoto.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesProxy = new ArrayList<>();
		predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostSearch.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostPhoto.get("postUUID"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootPostPhoto.get("hasPrincipal")));
		predicates.add(builder.notEqual(rootPost.get("id"), post.getId()));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		predicates.add(builder.notEqual(rootCompany.get("id"), companyId));
		if(!StringUtils.isEmpty(post.getKeysword())) {
			final String[] keysword = post.getKeysword().split(",");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String keyword : keysword) {
				final Expression<String> key = builder.lower(rootPost.get("keysword"));
				predicatesKey.add(builder.like(key, "%" + keyword.toLowerCase() + "%"));
			}
			predicatesProxy.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		if(!sectors.isEmpty()) {
			final Join<Company, Activity> joinActivity = rootCompany.join("activities");
			predicatesProxy.add(builder.in(joinActivity.get("sector")).value(sectors));
		}
		if(!wilayas.isEmpty()) {
			final Join<Company, CompanyAddress> joinAddress = rootCompany.join("addresses");
			predicatesProxy.add(builder.in(joinAddress.get("wilaya")).value(wilayas));
		}
		predicatesProxy.add(builder.equal(rootPost.get("service"), post.getService()));
		predicates.add(builder.or(predicatesProxy.toArray(new Predicate[0])));
		criteriaQuery.select(builder.construct(PostSimilarScreen.class, rootPost, rootPostDetail, rootPostPhoto, rootSeo.get("url"), rootPostSearch.get("simultude"), 
				rootPost.get("title"), rootPost.get("modifiedDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootPostSearch.get("simultude")), builder.desc(rootPost.get("modifiedDate")), 
				builder.asc(rootPost.get("title")));
		final TypedQuery<PostSimilarScreen> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<PostSimilarScreen> findPostSourcesList(final UUID postId, final Long companyId, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostSimilarScreen> criteriaQuery = builder.createQuery(PostSimilarScreen.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostDetail> rootPostDetail = criteriaQuery.from(PostDetail.class);
		final Root<PostSearch> rootPostSearch = criteriaQuery.from(PostSearch.class);
		final Root<PostPhoto> rootPostPhoto = criteriaQuery.from(PostPhoto.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPostDetail.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostSearch.get("id"), rootPost.get("id")));
		predicates.add(builder.equal(rootPostPhoto.get("postUUID"), rootPost.get("id")));
		predicates.add(builder.equal(rootPost.get("companyId"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.notEqual(rootPost.get("id"), postId));
		predicates.add(builder.isTrue(rootPostPhoto.get("hasPrincipal")));
		criteriaQuery.select(builder.construct(PostSimilarScreen.class, rootPost, rootPostDetail, rootPostPhoto, rootSeo.get("url"), rootPost.get("title"), rootPost.get("modifiedDate"))).distinct(true);
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootPost.get("modifiedDate")), builder.asc(rootPost.get("title")));
		final TypedQuery<PostSimilarScreen> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findAllPostCampaignMini(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
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
	public Long countAllRecyclePostCriteria(final Long companyId, final String filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isTrue(root.get("hasTrashed")));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(root.get("categoryId")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(root.get("categoryId"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<RecyclePostLine> findAllRecyclePostCriteria(final Long companyId, final String filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<RecyclePostLine> criteriaQuery = builder.createQuery(RecyclePostLine.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<User> rootUser = criteriaQuery.from(User.class);
		final Subquery<String> subquery = criteriaQuery.subquery(String.class);
		final Root<Category> subroot = subquery.from(Category.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPost.get("autorId"), rootUser.get("id")));
		predicates.add(builder.isTrue(rootPost.get("hasTrashed")));
		final Expression<String> username = builder.concat(rootUser.get("firstName"), builder.concat(" ", rootUser.get("lastName")));
		if(!StringUtils.isEmpty(filter)) {
			if(filter.equals("empty")) {
				predicates.add(builder.isNull(rootPost.get("categoryId")));
			} else {
				final UUID filterUUID = parseFilterUUID(filter);
				if(filterUUID != null) {
					predicates.add(builder.equal(rootPost.get("categoryId"), filterUUID));
				}
			}
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootPost.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subquery.select(subroot.get("name")).where(builder.equal(subroot.get("id"), rootPost.get("categoryId")));
		final Expression<String> categoryname = subquery.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootPost.get("modifiedDate")) : builder.asc(rootPost.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootPost.get("title")) : builder.asc(rootPost.get("title"));
		}
		criteriaQuery.select(builder.construct(RecyclePostLine.class, rootPost, username, categoryname));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<RecyclePostLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Object[] countStatsPostCriteria(final Long companyId, final boolean service) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final Subquery<Long> subqueryPublished = criteriaQuery.subquery(Long.class);
		final Root<Post> subrootPublished = subqueryPublished.from(Post.class);
		final Subquery<Long> subqueryTrashed = criteriaQuery.subquery(Long.class);
		final Root<Post> subrootTrashed = subqueryTrashed.from(Post.class);
		subqueryPublished.select(builder.count(subrootPublished)).where(builder.and(builder.equal(subrootPublished.get("companyId"), companyId), 
				builder.equal(subrootPublished.get("service"), service), builder.isTrue(subrootPublished.get("hasPublished"))));
		subqueryTrashed.select(builder.count(subrootTrashed)).where(builder.and(builder.equal(subrootTrashed.get("companyId"), companyId), 
				builder.equal(subrootTrashed.get("service"), service), builder.isTrue(subrootTrashed.get("hasTrashed"))));
		final Expression<Long> countPublished = subqueryPublished.getSelection();
		final Expression<Long> countTrashed = subqueryTrashed.getSelection();
		criteriaQuery.multiselect(builder.count(root), countPublished, countTrashed).where(builder.and(builder.equal(root.get("companyId"), companyId), 
				builder.equal(root.get("service"), service)));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAnalyticPostActivity(final Long companyId, final DateTime begin, final DateTime end, final boolean update) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.between(update ? root.get("modifiedDate") : root.get("createdDate"), begin, end));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countPublishedPost(final Long companyId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> root = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		predicates.add(builder.isFalse(root.get("hasTrashed")));
		predicates.add(builder.isTrue(root.get("hasPublished")));
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllActivePost() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.count(rootPost)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<PostMapsite> findAllPostMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PostMapsite> criteriaQuery = builder.createQuery(PostMapsite.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<Company> rootCompany = criteriaQuery.from(Company.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
		predicates.add(builder.equal(rootCompany.get("id"), rootSeo.get("companyId")));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootCompany.get("enabled")));
		predicates.add(builder.isTrue(rootCompany.get("active")));
		predicates.add(builder.isFalse(rootCompany.get("locked")));
		criteriaQuery.select(builder.construct(PostMapsite.class, rootPost.get("identify"), rootPost.get("modifiedDate"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootPost.get("modifiedDate")));
		final TypedQuery<PostMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllPost(final Integer filter, final boolean published) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.between(rootPost.get("modifiedDate"), ParseUtil.getBeginDate(filter), ParseUtil.getEndDate(filter)));
		}
		if(published) {
			final Root<Company> rootCompany = criteriaQuery.from(Company.class);
			predicates.add(builder.equal(rootPost.get("companyId"), rootCompany.get("id")));
			predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
			predicates.add(builder.isTrue(rootPost.get("hasPublished")));
			predicates.add(builder.isTrue(rootCompany.get("enabled")));
			predicates.add(builder.isTrue(rootCompany.get("active")));
			predicates.add(builder.isFalse(rootCompany.get("locked")));
		}
		if(predicates.isEmpty()) {
			criteriaQuery.select(builder.count(rootPost));
		} else {
			criteriaQuery.select(builder.count(rootPost)).where(predicates.toArray(new Predicate[0]));
		}
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<NewsletterItem> findAllNewsletterItem(final Long companyId, final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<NewsletterItem> criteriaQuery = builder.createQuery(NewsletterItem.class);
		final Root<Post> rootPost = criteriaQuery.from(Post.class);
		final Root<PostPhoto> rootPhoto = criteriaQuery.from(PostPhoto.class);
		final Root<CompanySeo> rootSeo = criteriaQuery.from(CompanySeo.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootPost.get("companyId"), companyId));
		predicates.add(builder.equal(rootPost.get("id"), rootPhoto.get("postUUID")));
		predicates.add(builder.equal(rootSeo.get("companyId"), companyId));
		predicates.add(builder.isFalse(rootPost.get("hasTrashed")));
		predicates.add(builder.isTrue(rootPost.get("hasPublished")));
		predicates.add(builder.isTrue(rootPhoto.get("hasPrincipal")));
		predicates.add(builder.in(rootPost.get("id")).value(lines));
		criteriaQuery.select(builder.construct(NewsletterItem.class, rootPost, rootPhoto.get("photoUUID"), rootSeo.get("url")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootPost.get("modifiedDate")));
		final TypedQuery<NewsletterItem> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

}
