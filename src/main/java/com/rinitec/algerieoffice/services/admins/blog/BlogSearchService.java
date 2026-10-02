package com.rinitec.algerieoffice.services.admins.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogAnalyticRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogExplorerMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogHomeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMarketMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogWidgetMini;

@Service
public class BlogSearchService implements IBlogSearchService {

	private BlogRepository blogRepository;
	private BlogAnalyticRepository blogAnalyticRepository;
	
	@Autowired
	public BlogSearchService(BlogRepository blogRepository, BlogAnalyticRepository blogAnalyticRepository) {
		this.blogRepository = blogRepository;
		this.blogAnalyticRepository = blogAnalyticRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findBlogExplorerList(final Integer filter, final String search, final String keyword, final Long autorId, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = blogRepository.countAllBlogExplorer(filter, search, keyword, autorId);
		final List<BlogWidgetMini> lines = countResult == 0L ? new ArrayList<BlogWidgetMini>() 
				: blogRepository.findAllBlogExplorer(filter, search, keyword, autorId, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogMini> findLastBlogMini(final String blogId, final int sort, final int limit) {
		try {
			return blogRepository.findLastBlogMini(UUID.fromString(blogId), sort, limit);
		} catch (IllegalArgumentException e) {}
		return new ArrayList<BlogMini>();
	}
	
	@Transactional
	private final void incrementSimultudes(final List<BlogSimultudeMini> lines) {
		if(!lines.isEmpty()) {
			final List<UUID> linesUUID = new ArrayList<UUID>();
			for (final BlogSimultudeMini blog : lines) {
				linesUUID.add(blog.getId());
			}
			blogAnalyticRepository.incrementSimultudes(linesUUID);
		}
	}
	
	@Override
	@Transactional
	public List<BlogSimultudeMini> findSimultudeBlogMini(final String blogId, final int category, final String language, final String keysword, final int limit) {
		try {
			final List<BlogSimultudeMini> lines = blogRepository.findSimultudeBlogMini(UUID.fromString(blogId), category, language, keysword, limit);
			incrementSimultudes(lines);
			return lines;
		} catch (IllegalArgumentException e) {}
		return new ArrayList<BlogSimultudeMini>();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogNewsMini> findBlogNewsMini(final int limit) {
		return blogRepository.findLastBlogNewsMini(limit);
	}
	
	@Override
	@Transactional
	public void incrementBlogFollow(final String blogId) {
		try {
			blogAnalyticRepository.incrementFollow(UUID.fromString(blogId));
		} catch (IllegalArgumentException e) {}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogExplorerMini> findExplorerBlogMini(final int limit) {
		return blogRepository.findLastExplorerBlogMini(limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlogMarketMini findOneBlogMarketMini() {
		try {
			return blogRepository.findOneBlogMarketMini();
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogMarketMini> findLastBlogMarketMini(final int limit) {
		return blogRepository.findLastBlogMarketMini(limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<BlogHomeMini> findLastBlogHomeMini(final int limit) {
		return blogRepository.findLastBlogHomeMini(limit);
	}
	
}
