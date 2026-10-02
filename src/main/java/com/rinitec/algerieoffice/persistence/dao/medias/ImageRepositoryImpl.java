package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.medias.Image;

@Repository
public class ImageRepositoryImpl implements ImageRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Image> findProxyImages(final Long companyId, final String search, final int page, final int rows) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Image> criteriaQuery = builder.createQuery(Image.class);
		final Root<Image> root = criteriaQuery.from(Image.class);
		final List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("companyId"), companyId));
		if(!StringUtils.isEmpty(search)) {
			final Expression<String> filename = builder.lower(root.get("filename"));
			predicates.add(builder.like(filename, "%" + search.toLowerCase() + "%"));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0])).orderBy(builder.desc(root.get("uploadDate")));
		final TypedQuery<Image> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult((page - 1) * rows).setMaxResults(rows);
		return query.getResultList();
	}
	
}
