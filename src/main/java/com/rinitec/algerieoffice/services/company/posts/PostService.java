package com.rinitec.algerieoffice.services.company.posts;

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

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostPhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.company.posts.CategoryForm;
import com.rinitec.algerieoffice.web.form.company.posts.PostForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.posts.AnalyticPost;
import com.rinitec.algerieoffice.web.modal.company.posts.CategoryLine;
import com.rinitec.algerieoffice.web.modal.company.posts.PostAccess;
import com.rinitec.algerieoffice.web.modal.company.posts.PostLine;
import com.rinitec.algerieoffice.web.modal.company.posts.PostStatLine;

@Service
public class PostService implements IPostService {

	private PostRepository postRepository;
	private PostDetailRepository postDetailRepository;
	private PostSearchRepository postSearchRepository;
	private PostPhotoRepository postPhotoRepository;
	private CategoryRepository categoryRepository;
	private IPhotoService photoService;
	
	@Autowired
	public PostService(PostRepository postRepository, PostDetailRepository postDetailRepository, PostSearchRepository postSearchRepository, 
			PostPhotoRepository postPhotoRepository, CategoryRepository categoryRepository, IPhotoService photoService) {
		this.postRepository = postRepository;
		this.postDetailRepository = postDetailRepository;
		this.postSearchRepository = postSearchRepository;
		this.postPhotoRepository = postPhotoRepository;
		this.categoryRepository = categoryRepository;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countPost(final Long companyId) {
		return postRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countTrashedPost(final Long companyId) {
		return postRepository.countByCompanyIdAndHasTrashed(companyId, true);
	}
	
	private final PostForm parsePostForm(final Post post, final PostDetail postDetail, final List<PostPhoto> postPhotos) {
		final PostForm postForm = new PostForm();
		postForm.setId(post.getId().toString());
		postForm.setCompanyId(post.getCompanyId());
		postForm.setService(post.getService());
		postForm.setTitle(post.getTitle());
		postForm.setIdentify(post.getIdentify());
		postForm.setDescription(post.getDescription());
		postForm.setDetail(new String(postDetail.getDetail()));
		postForm.setCategory(post.getCategoryId() != null ? post.getCategoryId().toString() : null);
		postForm.setKeysword(post.getKeysword());
		postForm.setUrlExtern(postDetail.getUrlExtern());
		postForm.setPriceType(postDetail.getPriceType());
		postForm.setPriceValue(postDetail.getPriceValue() != null ? String.valueOf(postDetail.getPriceValue()) : null);
		postForm.setPriceParrain(postDetail.getPriceParrain());
		postForm.setPricePrecision(postDetail.getPricePrecision());
		postForm.setLabelNew(postDetail.getLabelNew());
		postForm.setLabelExclusif(postDetail.getLabelExclusif());
		postForm.setHasPublished(post.getHasPublished());
		for (int i = 0; i < postPhotos.size(); i++) {
			final PostPhoto postPhoto = postPhotos.get(i);
			postForm.getTextsAlt().add(postPhoto.getTextAlt());
			postForm.getPhotosUUID().add(postPhoto.getPhotoUUID().toString());
			if(postPhoto.getHasPrincipal()) {
				postForm.setPhotoPrincipal(i);
			}
		}
		return postForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PostForm readPostForm(final String id, final Long companyId) {
		try {
			final Optional<Post> uOptional = postRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Post post = uOptional.get();
				final PostDetail postDetail = postDetailRepository.findById(post.getId()).get();
				final List<PostPhoto> postPhotos = postPhotoRepository.findByPostUUID(post.getId());
				return parsePostForm(post, postDetail, postPhotos);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Post postPost(final Post post, final PostForm postForm, final Long userId) {
		post.setService(postForm.getService());
		post.setTitle(postForm.getTitle());
		post.setIdentify(postForm.getIdentify());
		post.setDescription(postForm.getDescription());
		post.setKeysword(postForm.getKeysword());
		post.setCategoryId(postForm.isPresentCategoryId() ? UUID.fromString(postForm.getCategory()) : null);
		post.setModifiedDate(new DateTime(Date.from(Instant.now())));
		post.setAutorId(userId);
		post.setHasPublished(postForm.getHasPublished());
		return postRepository.save(post);
	}
	
	@Transactional
	private final PostDetail postPostDetail(final PostDetail postDetail, final PostForm postForm) {
		postDetail.setDetail(postForm.getDetail().getBytes());
		postDetail.setUrlExtern(!StringUtils.isEmpty(postForm.getUrlExtern()) ? postForm.getUrlExtern() : null);
		postDetail.setPriceType(postForm.getPriceType());
		postDetail.setPriceValue(!StringUtils.isEmpty(postForm.getPriceValue()) ? Integer.valueOf(postForm.getPriceValue()) : null);
		postDetail.setPriceParrain(!StringUtils.isEmpty(postForm.getPriceParrain()) ? postForm.getPriceParrain() : null);
		postDetail.setPricePrecision(!StringUtils.isEmpty(postForm.getPricePrecision()) ? postForm.getPricePrecision() : null);
		postDetail.setLabelNew(postForm.getLabelNew());
		postDetail.setLabelExclusif(postForm.getLabelExclusif());
		return postDetailRepository.save(postDetail);
	}
	
	@Transactional
	private final PostSearch creatPostSearch(final Post post) {
		final PostSearch postSearch = new PostSearch(post);
		return postSearchRepository.save(postSearch);
	}
	
	@Transactional
	private final void createPostPhotos(final UUID postId, final PostForm postForm) {
		final List<PostPhoto> postsPhotos = new ArrayList<PostPhoto>();
		for(int i = 0; i < postForm.getTextsAlt().size(); i++) {
			final Photo photo = photoService.addPhoto(postForm.getFiles()[i], postForm.getCompanyId(), PhotoType.post);
			final PostPhoto postPhoto = new PostPhoto();
			postPhoto.setPhotoUUID(photo.getId());
			postPhoto.setPostUUID(postId);
			postPhoto.setTextAlt(postForm.getTextsAlt().get(i));
			postPhoto.setHasPrincipal(postForm.getPhotoPrincipal() == i);
			postsPhotos.add(postPhoto);
		}
		postPhotoRepository.saveAll(postsPhotos);
	}
	
	@Override
	@Transactional
	public Post addPost(final PostForm postForm, final int maxKeysword, final Long userId) {
		if(postForm.getTextsAlt().isEmpty()) {
			throw new EmptyElementException("message.input.postphoto");
		}
		if(postRepository.findIdByIdentify(postForm.getCompanyId(), postForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(postForm.isPresentCategoryId() && !categoryRepository.existsById(UUID.fromString(postForm.getCategory()))) {
			throw new NotFoundException("message.input.notfound");
		}
		if(!StringUtils.isEmpty(postForm.getKeysword()) && postForm.getKeysword().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(!StringUtils.isEmpty(postForm.getUrlExtern()) && postDetailRepository.existsByUrlExtern(postForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Post post = postPost(new Post(postForm.getCompanyId()), postForm, userId);
		postPostDetail(new PostDetail(post), postForm);
		creatPostSearch(post);
		createPostPhotos(post.getId(), postForm);
		return post;
	}
	
	@Transactional
	private final void clearPostPhotos(final List<String> photosUUID) {
		photoService.deleteAllPhoto(photosUUID);
		postPhotoRepository.deleteLinesPostPhoto(ParseUtil.parseLinesUUID(photosUUID));
	}
	
	@Transactional
	private final PostPhoto updatePostPhoto(final UUID photoUUID, final String textAlt) {
		final PostPhoto postPhoto = postPhotoRepository.findById(photoUUID).get();
		postPhoto.setTextAlt(textAlt);
		return postPhotoRepository.save(postPhoto);
	}
	
	@Transactional
	private final List<UUID> updatePostPhotos(final UUID postId, final PostForm postForm) {
		int j = 0;
		final List<UUID> photosUUID = new ArrayList<UUID>();
		final List<PostPhoto> postsPhotos = new ArrayList<PostPhoto>();
		for(int i = 0; i < postForm.getPhotosUUID().size(); i++) {
			if(postForm.getPhotosUUID().get(i).equals("-1")) {
				if(postForm.getUpdated().get(i).equals("-1")) {
					final Photo photo = photoService.addPhoto(postForm.getFiles()[j++], postForm.getCompanyId(), PhotoType.post);
					final PostPhoto postPhoto = new PostPhoto();
					postPhoto.setPhotoUUID(photo.getId());
					postPhoto.setPostUUID(postId);
					postPhoto.setTextAlt(postForm.getTextsAlt().get(i));
					postPhoto.setHasPrincipal(false);
					postsPhotos.add(postPhoto);
					photosUUID.add(photo.getId());
				} else {
					final PostPhoto postPhoto = updatePostPhoto(UUID.fromString(postForm.getUpdated().get(i)), postForm.getTextsAlt().get(i));
					photoService.updatePhoto(postPhoto.getPhotoUUID(), postForm.getFiles()[j++]);
					photosUUID.add(postPhoto.getPhotoUUID());
				}
			} else if(postForm.getPhotosUUID().get(i).equals("0")) {
				final UUID photoUUID = UUID.fromString(postForm.getUpdated().get(i));
				updatePostPhoto(photoUUID, postForm.getTextsAlt().get(i));
				photosUUID.add(photoUUID);
			} else {
				photosUUID.add(UUID.fromString(postForm.getPhotosUUID().get(i)));
			}
		}
		if(!postsPhotos.isEmpty()) {
			postPhotoRepository.saveAll(postsPhotos);
		}
		return photosUUID;
	}
	
	@Transactional
	private final void updatePrincipalPhoto(final UUID postId, final UUID photoId) {
		postPhotoRepository.clearHasPrincipalByPostId(postId);
		postPhotoRepository.updateHasPrincipalByPhotoId(photoId);
	}
	
	@Override
	@Transactional
	public Post updatePost(final PostForm postForm, final int maxKeysword, final Long userId) {
		if(postForm.getTextsAlt().isEmpty()) {
			throw new EmptyElementException("message.input.postphoto");
		}
		final Optional<Post> uOptional = postRepository.findById(UUID.fromString(postForm.getId()));
		if(uOptional.isPresent() && postForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Post post = uOptional.get();
			final PostDetail postDetail = postDetailRepository.findById(post.getId()).get();
			final String categoryId = post.getCategoryId() != null ? post.getCategoryId().toString() : null;
			if(!postForm.getIdentify().equalsIgnoreCase(post.getIdentify()) 
					&& postRepository.findIdByIdentify(postForm.getCompanyId(), postForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(postForm.isPresentCategoryId() && !postForm.getCategory().equals(categoryId) 
					&& !categoryRepository.existsById(UUID.fromString(postForm.getCategory()))) {
				throw new NotFoundException("message.input.notfound");
			}
			if(!StringUtils.isEmpty(postForm.getKeysword()) && postForm.getKeysword().split(",").length > maxKeysword) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			if(!StringUtils.isEmpty(postForm.getUrlExtern()) && !postForm.getUrlExtern().equalsIgnoreCase(postDetail.getUrlExtern()) 
					&& postDetailRepository.existsByUrlExtern(postForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			postPost(post, postForm, userId);
			postPostDetail(postDetail, postForm);
			if(postForm.isUpdateFile()) {
				if(!postForm.getTrashed().isEmpty()) {
					clearPostPhotos(postForm.getTrashed());
				}
				final List<UUID> photosUUID = updatePostPhotos(post.getId(), postForm);
				updatePrincipalPhoto(post.getId(), photosUUID.get(postForm.getPhotoPrincipal()));
			}
			return post;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPostsList(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = postRepository.countAllPostCriteria(companyId, filter, search);
		final List<PostLine> lines = countResult == 0L ? new ArrayList<PostLine>() 
				: postRepository.findAllPostCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Post trashPost(final String id, final Long companyId) {
		try {
			final Optional<Post> uOptional = postRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Post post = uOptional.get();
			post.setHasTrashed(true);
			return postRepository.save(post);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashPosts(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) postRepository.countPosts(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			postRepository.trashPosts(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void trashAllPosts(final Long companyId) {
		postRepository.trashAllPosts(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllChoseCategory(final Long companyId, final String categoryId) {
		return categoryRepository.findAllChoseCategoryMini(companyId, StringUtils.isEmpty(categoryId) ? null : UUID.fromString(categoryId));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllFilterCategory(final Long companyId) {
		return categoryRepository.findAllFilterCategoryMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllCategory(final Long companyId) {
		return categoryRepository.findAllCategoryMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CategoryForm readCategoryForm(final String id, final Long companyId) {
		try {
			final Optional<Category> uOptional = categoryRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Category category = uOptional.get();
				final CategoryForm categoryForm = new CategoryForm();
				categoryForm.setId(id);
				categoryForm.setCompanyId(companyId);
				categoryForm.setName(category.getName());
				categoryForm.setIdentify(category.getIdentify());
				categoryForm.setParentUUID(category.getParentUUID() != null ? category.getParentUUID().toString() : null);
				categoryForm.setHasParent(categoryRepository.existsByParentUUID(category.getId()));
				categoryForm.setDescription(category.getDescription());
				categoryForm.setHasPingled(category.getHasPingled());
				return categoryForm;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public Category addCategory(final CategoryForm categoryForm) {
		if(categoryRepository.findIdByName(categoryForm.getCompanyId(), categoryForm.getName()).isPresent()) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(categoryRepository.findIdByIdentify(categoryForm.getCompanyId(), categoryForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(categoryForm.isPresentParentUUID() && !categoryRepository.existsById(UUID.fromString(categoryForm.getParentUUID()))) {
			throw new NotFoundException("message.input.notfound");
		}
		final Category category = new Category();
		category.setCompanyId(categoryForm.getCompanyId());
		category.setName(categoryForm.getName());
		category.setIdentify(categoryForm.getIdentify());
		category.setDescription(categoryForm.getDescription());
		category.setHasPingled(categoryForm.getHasPingled());
		if(categoryForm.isPresentParentUUID()) {
			category.setParentUUID(UUID.fromString(categoryForm.getParentUUID()));
		}
		return categoryRepository.save(category);
	}
	
	@Override
	@Transactional
	public Category updateCategory(final CategoryForm categoryForm) {
		final Optional<Category> uOptional = categoryRepository.findById(UUID.fromString(categoryForm.getId()));
		if(uOptional.isPresent() && categoryForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Category category = uOptional.get();
			final String parentUUID = category.getParentUUID() != null ? category.getParentUUID().toString() : null;
			if(!categoryForm.getName().equalsIgnoreCase(category.getName()) 
					&& categoryRepository.findIdByName(categoryForm.getCompanyId(), categoryForm.getName()).isPresent()) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			if(!categoryForm.getIdentify().equalsIgnoreCase(category.getIdentify()) 
					&& categoryRepository.findIdByIdentify(categoryForm.getCompanyId(), categoryForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(categoryForm.isPresentParentUUID() && !categoryForm.getParentUUID().equals(parentUUID) 
					&& !categoryRepository.existsById(UUID.fromString(categoryForm.getParentUUID()))) {
				throw new NotFoundException("message.input.notfound");
			}
			category.setName(categoryForm.getName());
			category.setIdentify(categoryForm.getIdentify());
			category.setDescription(categoryForm.getDescription());
			category.setHasPingled(categoryForm.getHasPingled());
			if(!categoryForm.getHasParent()) {
				category.setParentUUID(categoryForm.isPresentParentUUID() ? UUID.fromString(categoryForm.getParentUUID()) : null);
			}
			return categoryRepository.save(category);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findCategoriesList(final Long companyId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = categoryRepository.countAllCategoryCriteria(companyId, filter, search);
		final List<CategoryLine> lines = countResult == 0L ? new ArrayList<CategoryLine>() 
				: categoryRepository.findAllCategoryCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Category deleteCategory(final String id, final Long companyId) {
		try {
			final Optional<Category> uOptional = categoryRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Category category = uOptional.get();
			categoryRepository.trashParentUUID(category.getId());
			postRepository.trashCategoryId(category.getId());
			categoryRepository.delete(category);
			return category;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteCategories(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) categoryRepository.countCategories(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			categoryRepository.trashParentsUUID(linesUUID);
			postRepository.trashCategories(linesUUID);
			categoryRepository.deleteCategories(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllCategories(final Long companyId) {
		postRepository.trashAllCategories(companyId);
		categoryRepository.deleteByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticPost readAnalyticPost(final Long companyId) {
		final Long[] countPost = new Long[3];
		final Long[] countService = new Long[3];
		final Object[] countPosts = postRepository.countStatsPostCriteria(companyId, false);
		final Object[] countServices = postRepository.countStatsPostCriteria(companyId, true);
		for(int i = 0; i < 3; i++) {
			countPost[i] = (Long) countPosts[i];
			countService[i] = (Long) countServices[i];
		}
		return new AnalyticPost(countPost, countService);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPostStatsList(final Long companyId, final Boolean filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = postSearchRepository.countAllPostStatCriteria(companyId, filter, search);
		final List<PostStatLine> lines = countResult == 0L ? new ArrayList<PostStatLine>() 
				: postSearchRepository.findAllPostStatCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PostAccess> findPostAccessList(final Long companyId, final int limit) {
		return postSearchRepository.findPostAccessList(companyId, limit);
	}
	
}
