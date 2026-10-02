package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.ArrayList;
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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogAnalytic;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogDetail;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogLike;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogLine;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogStatLine;
import com.rinitec.algerieoffice.web.modal.mapsite.BlogMapsite;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogExplorerMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogHomeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMarketMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogWidgetMini;

@Repository
public class BlogRepositoryImpl implements BlogRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countAllBlogCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		if(filter != null) {
			predicates.add(builder.equal(root.get("category"), filter));
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
	public List<BlogLine> findAllBlogCriteria(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogLine> criteriaQuery = builder.createQuery(BlogLine.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<Autor> rootAutor = criteriaQuery.from(Autor.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("autorId"), rootAutor.get("id")));
		if(filter != null) {
			predicates.add(builder.equal(rootBlog.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootBlog.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootBlog.get("modifiedDate")) : builder.asc(rootBlog.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootBlog.get("title")) : builder.asc(rootBlog.get("title")); break;
		case 3: order = hasDesc ? builder.desc(rootBlog.get("category")) : builder.asc(rootBlog.get("category")); break;
		case 4: order = hasDesc ? builder.desc(rootAutor.get("autorname")) : builder.asc(rootAutor.get("autorname")); break;
		default: order = hasDesc ? builder.desc(rootBlog.get("viewCount")) : builder.asc(rootBlog.get("viewCount"));
		}
		criteriaQuery.select(builder.construct(BlogLine.class, rootBlog, rootAutor.get("autorname"), countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<BlogLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllBlogStatsCriteria(final Integer filter, final String search) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<BlogStatLine> findAllBlogStatsCriteria(final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogStatLine> criteriaQuery = builder.createQuery(BlogStatLine.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		if(filter != null) {
			predicates.add(builder.equal(rootBlog.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootBlog.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootBlog.get("viewCount")) : builder.asc(rootBlog.get("viewCount")); break;
		case 2: order = hasDesc ? builder.desc(rootAnalytic.get("simultude")) : builder.asc(rootAnalytic.get("simultude")); break;
		case 3: order = hasDesc ? builder.desc(rootAnalytic.get("market")) : builder.asc(rootAnalytic.get("market")); break;
		case 4: order = hasDesc ? builder.desc(rootBlog.get("modifiedDate")) : builder.asc(rootBlog.get("modifiedDate")); break;
		default: order = hasDesc ? builder.desc(rootBlog.get("title")) : builder.asc(rootBlog.get("title"));
		}
		criteriaQuery.select(builder.construct(BlogStatLine.class, rootBlog, rootAnalytic, countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<BlogStatLine> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public Long countAllBlogExplorer(final Integer filter, final String search, final String keyword, final Long autorId) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("hasPublished")));
		if(filter != null) {
			predicates.add(builder.equal(root.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(root.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(keyword)) {
			final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
			final Expression<String> keysword = builder.lower(rootDetail.get("keysword"));
			predicates.add(builder.equal(root.get("id"), rootDetail.get("id")));
			predicates.add(builder.like(keysword, "%" + keyword.toLowerCase() + "%"));
		}
		if(autorId != null) {
			predicates.add(builder.equal(root.get("autorId"), autorId));
		}
		criteriaQuery.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<BlogWidgetMini> findAllBlogExplorer(final Integer filter, final String search, final String keyword, final Long autorId, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogWidgetMini> criteriaQuery = builder.createQuery(BlogWidgetMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		if(filter != null) {
			predicates.add(builder.equal(rootBlog.get("category"), filter));
		}
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> title = builder.lower(rootBlog.get("title"));
			predicates.add(builder.like(title, "%" + search.toLowerCase() + "%"));
		}
		if(!StringUtils.isEmpty(keyword)) {
			final Expression<String> keysword = builder.lower(rootDetail.get("keysword"));
			predicates.add(builder.like(keysword, "%" + keyword.toLowerCase() + "%"));
		}
		if(autorId != null) {
			predicates.add(builder.equal(rootBlog.get("autorId"), autorId));
		}
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		final Order order;
		switch (sort) {
		case 1: order = hasDesc ? builder.desc(rootBlog.get("modifiedDate")) : builder.asc(rootBlog.get("modifiedDate")); break;
		case 2: order = hasDesc ? builder.desc(rootBlog.get("viewCount")) : builder.asc(rootBlog.get("viewCount")); break;
		default: order = hasDesc ? builder.desc(rootBlog.get("title")) : builder.asc(rootBlog.get("title"));
		}
		criteriaQuery.select(builder.construct(BlogWidgetMini.class, rootBlog, rootDetail.get("photoUUID"), countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<BlogWidgetMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
	@Override
	public List<BlogMini> findLastBlogMini(final UUID blogId, final int sort, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogMini> criteriaQuery = builder.createQuery(BlogMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.notEqual(rootBlog.get("id"), blogId));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		final Order order;
		switch (sort) {
		case 1: order = builder.desc(rootBlog.get("viewCount")); break;
		default: order = builder.desc(rootBlog.get("modifiedDate"));
		}
		criteriaQuery.select(builder.construct(BlogMini.class, rootBlog.get("title"), rootBlog.get("identify")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(order);
		final TypedQuery<BlogMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<BlogSimultudeMini> findSimultudeBlogMini(final UUID blogId, final int category, final String language, final String keysword, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogSimultudeMini> criteriaQuery = builder.createQuery(BlogSimultudeMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		final List<Predicate> predicatesProxy = new ArrayList<>();
		predicates.add(builder.notEqual(rootBlog.get("id"), blogId));
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		predicatesProxy.add(builder.equal(rootBlog.get("category"), category));
		predicatesProxy.add(builder.equal(rootBlog.get("language"), language));
		if(!StringUtils.isEmpty(keysword)) {
			final String[] keys = keysword.split(",");
			final List<Predicate> predicatesKey = new ArrayList<>();
			for (final String key : keys) {
				final Expression<String> keyword = builder.lower(rootDetail.get("keysword"));
				predicatesKey.add(builder.like(keyword, "%" + key.toLowerCase() + "%"));
			}
			predicatesProxy.add(builder.or(predicatesKey.toArray(new Predicate[0])));
		}
		predicates.add(builder.or(predicatesProxy.toArray(new Predicate[0])));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		criteriaQuery.select(builder.construct(BlogSimultudeMini.class, rootBlog, rootDetail.get("photoUUID"), countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAnalytic.get("simultude")));
		final TypedQuery<BlogSimultudeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<BlogNewsMini> findLastBlogNewsMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogNewsMini> criteriaQuery = builder.createQuery(BlogNewsMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Root<Autor> rootAutor = criteriaQuery.from(Autor.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootBlog.get("autorId"), rootAutor.get("id")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		criteriaQuery.select(builder.construct(BlogNewsMini.class, rootBlog, rootDetail.get("photoUUID"), rootAutor.get("autorname"), rootAutor.get("identify")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(rootBlog.get("modifiedDate")));
		final TypedQuery<BlogNewsMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<BlogExplorerMini> findLastExplorerBlogMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogExplorerMini> criteriaQuery = builder.createQuery(BlogExplorerMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		criteriaQuery.select(builder.construct(BlogExplorerMini.class, rootBlog, rootDetail.get("photoUUID"), countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAnalytic.get("market")), builder.asc(rootBlog.get("modifiedDate")));
		final TypedQuery<BlogExplorerMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public BlogMarketMini findOneBlogMarketMini() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogMarketMini> criteriaQuery = builder.createQuery(BlogMarketMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		criteriaQuery.select(builder.construct(BlogMarketMini.class, rootBlog, rootDetail.get("photoUUID")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAnalytic.get("market")));
		final TypedQuery<BlogMarketMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(1);
		return query.getSingleResult();
	}
	
	@Override
	public List<BlogMarketMini> findLastBlogMarketMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogMarketMini> criteriaQuery = builder.createQuery(BlogMarketMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogDetail> rootDetail = criteriaQuery.from(BlogDetail.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootDetail.get("id")));
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		criteriaQuery.select(builder.construct(BlogMarketMini.class, rootBlog, rootDetail.get("photoUUID")));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAnalytic.get("market")));
		final TypedQuery<BlogMarketMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<BlogHomeMini> findLastBlogHomeMini(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogHomeMini> criteriaQuery = builder.createQuery(BlogHomeMini.class);
		final Root<Blog> rootBlog = criteriaQuery.from(Blog.class);
		final Root<BlogAnalytic> rootAnalytic = criteriaQuery.from(BlogAnalytic.class);
		final Subquery<Long> subqueryLike = criteriaQuery.subquery(Long.class);
		final Root<BlogLike> subrootLike = subqueryLike.from(BlogLike.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootBlog.get("id"), rootAnalytic.get("blogId")));
		predicates.add(builder.isTrue(rootBlog.get("hasPublished")));
		subqueryLike.select(builder.count(subrootLike)).where(builder.equal(subrootLike.get("blogId"), rootBlog.get("id")));
		final Expression<Long> countLike = subqueryLike.getSelection();
		criteriaQuery.select(builder.construct(BlogHomeMini.class, rootBlog, countLike));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootAnalytic.get("market")), builder.asc(rootBlog.get("modifiedDate")));
		final TypedQuery<BlogHomeMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Long countAllBlogMapsite() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		criteriaQuery.select(builder.count(root)).where(builder.isTrue(root.get("hasPublished")));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<BlogMapsite> findAllBlogMapsite(final int page, final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogMapsite> criteriaQuery = builder.createQuery(BlogMapsite.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		criteriaQuery.select(builder.construct(BlogMapsite.class, root.get("identify"), root.get("modifiedDate")));
		criteriaQuery.where(builder.isTrue(root.get("hasPublished"))).orderBy(builder.desc(root.get("modifiedDate")));
		final TypedQuery<BlogMapsite> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * limit).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<UUIDMini> findLastBlogNewsletter(final int limit) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<UUIDMini> criteriaQuery = builder.createQuery(UUIDMini.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		criteriaQuery.select(builder.construct(UUIDMini.class, root.get("id"), root.get("title")));
		criteriaQuery.where(builder.isTrue(root.get("hasPublished"))).orderBy(builder.desc(root.get("modifiedDate")));
		final TypedQuery<UUIDMini> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(0).setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<BlogNewsletterMini> findAllBlogNewsletterMini(final List<UUID> lines) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<BlogNewsletterMini> criteriaQuery = builder.createQuery(BlogNewsletterMini.class);
		final Root<Blog> root = criteriaQuery.from(Blog.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.isTrue(root.get("hasPublished")));
		predicates.add(builder.in(root.get("id")).value(lines));
		criteriaQuery.select(builder.construct(BlogNewsletterMini.class, root));
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("modifiedDate")));
		final TypedQuery<BlogNewsletterMini> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}
