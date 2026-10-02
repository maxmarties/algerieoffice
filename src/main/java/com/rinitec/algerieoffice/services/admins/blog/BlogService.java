package com.rinitec.algerieoffice.services.admins.blog;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.BannerType;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.AutorRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogAnalyticRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogAnalytic;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogDetail;
import com.rinitec.algerieoffice.persistence.modal.medias.Banner;
import com.rinitec.algerieoffice.services.medias.IBannerService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogForm;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogStatForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogLine;
import com.rinitec.algerieoffice.web.modal.admins.blog.BlogStatLine;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogInbox;

@Service
public class BlogService implements IBlogService {

	private BlogRepository blogRepository;
	private BlogDetailRepository blogDetailRepository;
	private BlogAnalyticRepository blogAnalyticRepository;
	private BlogLikeRepository blogLikeRepository;
	private AutorRepository autorRepository;
	private IBannerService bannerService;
	
	@Autowired
	public BlogService(BlogRepository blogRepository, BlogDetailRepository blogDetailRepository, BlogAnalyticRepository blogAnalyticRepository, 
			BlogLikeRepository blogLikeRepository, AutorRepository autorRepository, IBannerService bannerService) {
		this.blogRepository = blogRepository;
		this.blogDetailRepository = blogDetailRepository;
		this.blogAnalyticRepository = blogAnalyticRepository;
		this.blogLikeRepository = blogLikeRepository;
		this.autorRepository = autorRepository;
		this.bannerService = bannerService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByIdentify(final String identify) {
		return blogRepository.existsByIdentify(identify);
	}
	
	private final BlogForm parseBlogForm(final Blog blog, final BlogDetail blogDetail) {
		final BlogForm blogForm = new BlogForm();
		blogForm.setId(blog.getId().toString());
		blogForm.setTitle(blog.getTitle());
		blogForm.setIdentify(blog.getIdentify());
		blogForm.setCheckedIdentify(blog.getIdentify());
		blogForm.setDescription(blog.getDescription());
		blogForm.setDetail(new String(blogDetail.getDetail()));
		blogForm.setCategory(blog.getCategory());
		blogForm.setAutorId(blog.getAutorId());
		blogForm.setKeysword(blogDetail.getKeysword());
		blogForm.setLanguage(blog.getLanguage());
		blogForm.setHasPublished(blog.getHasPublished());
		blogForm.setHasAvatar(true);
		blogForm.setUrlAvatar(ConstraintesURL.URL_AOBNN + "?aobnId=".concat(blogDetail.getPhotoUUID().toString()));
		return blogForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlogForm readBlogForm(final String id) {
		try {
			final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Blog blog = uOptional.get();
				final BlogDetail blogDetail = blogDetailRepository.findById(blog.getId()).get();
				return parseBlogForm(blog, blogDetail);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Blog postBlog(final Blog blog, final BlogForm blogForm) {
		blog.setTitle(blogForm.getTitle());
		blog.setIdentify(blogForm.getIdentify());
		blog.setDescription(blogForm.getDescription());
		blog.setCategory(blogForm.getCategory());
		blog.setAutorId(blogForm.getAutorId());
		blog.setLanguage(blogForm.getLanguage());
		blog.setModifiedDate(new DateTime(Date.from(Instant.now())));
		blog.setHasPublished(blogForm.getHasPublished());
		return blogRepository.save(blog);
	}
	
	@Transactional
	private final BlogDetail postBlogDetail(final BlogDetail blogDetail, final BlogForm blogForm, final UUID photoUUID) {
		blogDetail.setKeysword(blogForm.getKeysword());
		blogDetail.setDetail(blogForm.getDetail().getBytes());
		blogDetail.setPhotoUUID(photoUUID);
		return blogDetailRepository.save(blogDetail);
	}
	
	@Transactional
	private final BlogAnalytic createBlogAnalytic(final UUID blogId) {
		final BlogAnalytic blogAnalytic = new BlogAnalytic(blogId);
		return blogAnalyticRepository.save(blogAnalytic);
	}
	
	@Override
	@Transactional
	public Blog addBlog(final BlogForm blogForm) {
		if(blogRepository.existsByIdentify(blogForm.getIdentify())) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(!autorRepository.existsById(blogForm.getAutorId())) {
			throw new NotFoundException("message.input.notfound");
		}
		if(!StringUtils.isEmpty(blogForm.getKeysword()) && blogForm.getKeysword().split(",").length > ConstraintesForm.MAX_KEYS_BLOG) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		final Banner banner = bannerService.addBanner(blogForm.getFile(), BannerType.blog);
		final Blog blog = postBlog(new Blog(), blogForm);
		postBlogDetail(new BlogDetail(blog), blogForm, banner.getId());
		createBlogAnalytic(blog.getId());
		return blog;
	}
	
	@Override
	@Transactional
	public Blog updateBlog(final BlogForm blogForm) {
		final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(blogForm.getId()));
		if(uOptional.isPresent()) {
			final Blog blog = uOptional.get();
			if(!blogForm.getIdentify().equalsIgnoreCase(blog.getIdentify()) && blogRepository.existsByIdentify(blogForm.getIdentify())) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(!blogForm.getAutorId().equals(blog.getAutorId()) && !autorRepository.existsById(blogForm.getAutorId())) {
				throw new NotFoundException("message.input.notfound");
			}
			if(!StringUtils.isEmpty(blogForm.getKeysword()) && blogForm.getKeysword().split(",").length > ConstraintesForm.MAX_KEYS_BLOG) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			final BlogDetail blogDetail = blogDetailRepository.findById(blog.getId()).get();
			if(blogForm.isHasFileChanged()) {
				bannerService.updateBanner(blogDetail.getPhotoUUID(), blogForm.getFile());
			}
			postBlog(blog, blogForm);
			postBlogDetail(blogDetail, blogForm, blogDetail.getPhotoUUID());
			return blog;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findBlogsList(final Integer filter, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = blogRepository.countAllBlogCriteria(filter, search);
		final List<BlogLine> lines = countResult == 0L ? new ArrayList<BlogLine>() 
				: blogRepository.findAllBlogCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Blog deleteBlog(final String id) {
		try {
			final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Blog blog = uOptional.get();
			final UUID photoId = blogDetailRepository.findPhotoUUIDById(blog.getId()).get();
			blogLikeRepository.deleteByBlogId(blog.getId());
			blogAnalyticRepository.deleteById(blog.getId());
			blogDetailRepository.deleteById(blog.getId());
			blogRepository.delete(blog);
			bannerService.deleteBanner(photoId);
			return blog;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteBlogs(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) blogRepository.countBlogs(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesPhoto = blogDetailRepository.findAllPhotoUUIDByIds(linesUUID);
			blogLikeRepository.deleteBlogsLike(linesUUID);
			blogAnalyticRepository.deleteBlogsAnalytic(linesUUID);
			blogDetailRepository.deleteBlogsDetail(linesUUID);
			blogRepository.deleteBlogs(linesUUID);
			bannerService.deleteAllBanners(linesPhoto);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> countFamilyBlog() {
		final List<String> lines = new ArrayList<String>();
		for(int i = 1; i <= ConstraintesURL.URL_FAMILY_BLOG.length; i++) {
			final Long count = blogRepository.countByCategoryAndHasPublished(i, true);
			lines.add(ParseUtil.getFormattedCount(count));
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlogAutorMini readAutor(final String identify) {
		final Autor autor = autorRepository.findByIdentify(identify);
		return autor != null ? new BlogAutorMini(autor) : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlogInbox readBlogInbox(final String identify) {
		final Blog blog = blogRepository.findByIdentify(identify);
		if(blog != null) {
			final BlogDetail blogDetail = blogDetailRepository.findById(blog.getId()).get();
			final Autor autor = autorRepository.findById(blog.getAutorId()).get();
			final Long countLike = blogLikeRepository.countByBlogId(blog.getId());
			return new BlogInbox(blog, blogDetail, autor, countLike);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findBlogStatsList(final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = blogRepository.countAllBlogStatsCriteria(filter, search);
		final List<BlogStatLine> lines = countResult == 0L ? new ArrayList<BlogStatLine>() 
				: blogRepository.findAllBlogStatsCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	private final BlogStatForm parseBlogStatForm(final Blog blog, final BlogAnalytic blogAnalytic) {
		final BlogStatForm blogStatForm = new BlogStatForm();
		blogStatForm.setId(blog.getId().toString());
		blogStatForm.setTitle(blog.getTitle());
		blogStatForm.setViewCount(blog.getViewCount());
		blogStatForm.setSimultude(blogAnalytic.getSimultude());
		blogStatForm.setMarket(blogAnalytic.getMarket());
		return blogStatForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BlogStatForm readBlogStatForm(final String id) {
		try {
			final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Blog blog = uOptional.get();
				final BlogAnalytic blogAnalytic = blogAnalyticRepository.findById(blog.getId()).get();
				return parseBlogStatForm(blog, blogAnalytic);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Blog updateBlogStats(final Blog blog, final BlogStatForm blogStatForm) {
		blog.setViewCount(blogStatForm.getViewCount());
		return blogRepository.save(blog);
	}
	
	@Transactional
	private final BlogAnalytic updateBlogAnalyticStats(final BlogAnalytic blogAnalytic, final BlogStatForm blogStatForm) {
		blogAnalytic.setSimultude(blogStatForm.getSimultude());
		blogAnalytic.setMarket(blogStatForm.getMarket());
		return blogAnalyticRepository.save(blogAnalytic);
	}
	
	@Override
	@Transactional
	public Blog updateBlogStat(final BlogStatForm blogStatForm) {
		final Optional<Blog> uOptional = blogRepository.findById(UUID.fromString(blogStatForm.getId()));
		if(uOptional.isPresent()) {
			final Blog blog = updateBlogStats(uOptional.get(), blogStatForm);
			updateBlogAnalyticStats(blogAnalyticRepository.findById(blog.getId()).get(), blogStatForm);
			return blog;
		}
		return null;
	}
	
}
