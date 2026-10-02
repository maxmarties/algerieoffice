package com.rinitec.algerieoffice.services.company.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignTargetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeLocationRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteActivityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.PromoteWilayaRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.CategoryRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostPhotoRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostSearchRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleAnnonceLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleEmployeLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePostLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePromoteLine;

@Service
public class RecycleService implements IRecycleService {

	private PostRepository postRepository;
	private PostDetailRepository postDetailRepository;
	private PostPhotoRepository postPhotoRepository;
	private PostSearchRepository postSearchRepository;
	private CategoryRepository categoryRepository;
	private PromoteRepository promoteRepository;
	private PromoteActivityRepository promoteActivityRepository;
	private PromoteWilayaRepository promoteWilayaRepository;
	private AnnonceRepository annonceRepository;
	private AnnonceDetailRepository annonceDetailRepository;
	private AnnonceActivityRepository annonceActivityRepository;
	private AnnonceWilayaRepository annonceWilayaRepository;
	private EmployeRepository employeRepository;
	private EmployeDetailRepository employeDetailRepository;
	private EmployeLocationRepository employeLocationRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private CampaignRepository campaignRepository;
	private CampaignTargetRepository campaignTargetRepository;
	private IFilereaderService filereaderService;
	private IPhotoService photoService;
	
	@Autowired
	public RecycleService(PostRepository postRepository, PostDetailRepository postDetailRepository, 
			PostPhotoRepository postPhotoRepository, PostSearchRepository postSearchRepository, CategoryRepository categoryRepository, 
			PromoteRepository promoteRepository, PromoteActivityRepository promoteActivityRepository, 
			PromoteWilayaRepository promoteWilayaRepository, AnnonceRepository annonceRepository,
			AnnonceDetailRepository annonceDetailRepository, AnnonceActivityRepository annonceActivityRepository, 
			AnnonceWilayaRepository annonceWilayaRepository, EmployeRepository employeRepository,
			EmployeDetailRepository employeDetailRepository, EmployeLocationRepository employeLocationRepository, 
			GuestDocumentRepository guestDocumentRepository, FavoriteDocumentRepository favoriteDocumentRepository, 
			CampaignRepository campaignRepository, CampaignTargetRepository campaignTargetRepository,
			IFilereaderService filereaderService, IPhotoService photoService) {
		this.postRepository = postRepository;
		this.postDetailRepository = postDetailRepository;
		this.postPhotoRepository = postPhotoRepository;
		this.postSearchRepository = postSearchRepository;
		this.categoryRepository = categoryRepository;
		this.promoteRepository = promoteRepository;
		this.promoteActivityRepository = promoteActivityRepository;
		this.promoteWilayaRepository = promoteWilayaRepository;
		this.annonceRepository = annonceRepository;
		this.annonceDetailRepository = annonceDetailRepository;
		this.annonceActivityRepository = annonceActivityRepository;
		this.annonceWilayaRepository = annonceWilayaRepository;
		this.employeRepository = employeRepository;
		this.employeDetailRepository = employeDetailRepository;
		this.employeLocationRepository = employeLocationRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.campaignRepository = campaignRepository;
		this.campaignTargetRepository = campaignTargetRepository;
		this.filereaderService = filereaderService;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPostsList(final Long companyId, final String filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = postRepository.countAllRecyclePostCriteria(companyId, filter, search);
		final List<RecyclePostLine> lines = countResult == 0L ? new ArrayList<RecyclePostLine>() 
				: postRepository.findAllRecyclePostCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	public Post restorePost(final String id, final Long companyId) {
		try {
			final Optional<Post> uOptional = postRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Post post = uOptional.get();
			post.setHasTrashed(false);
			return postRepository.save(post);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Post deletePost(final String id, final Long companyId) {
		try {
			final Optional<Post> uOptional = postRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Post post = uOptional.get();
			final UUID postId = post.getId();
			final List<UUID> linesPhoto = postPhotoRepository.findAllPhotoUUIDByPostId(postId);
			final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentId(postId, DocumentType.post);
			final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentId(postId, DocumentType.post);
			postDetailRepository.deleteById(postId);
			postSearchRepository.deleteById(postId);
			postPhotoRepository.deleteByPostUUID(postId);
			postRepository.delete(post);
			guestDocumentRepository.deleteByDocumentIdAndType(postId, DocumentType.post);
			favoriteDocumentRepository.deleteByDocumentIdAndType(postId, DocumentType.post);
			campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
			campaignRepository.deleteByDocumentIdAndType(postId, DocumentType.post);
			photoService.deleteAllPhotos(linesPhoto);
			filereaderService.deleteAllFilereaders(linesFiles);
			return post;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final void removePosts(final List<UUID> linesUUID) {
		final List<UUID> linesPhoto = postPhotoRepository.findAllPhotoUUIDByPostIds(linesUUID);
		final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentIds(linesUUID, DocumentType.post);
		final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentIds(linesUUID, DocumentType.post);
		postDetailRepository.deletePostDetails(linesUUID);
		postSearchRepository.deletePostSearchs(linesUUID);
		postPhotoRepository.deletePostPhotoByPosts(linesUUID);
		postRepository.deletePosts(linesUUID);
		guestDocumentRepository.deleteGuestDocumentByDocumentsIds(linesUUID, DocumentType.post);
		favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(linesUUID, DocumentType.post);
		campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
		campaignRepository.deleteCampaignsByDocumentsIds(linesUUID, DocumentType.post);
		photoService.deleteAllPhotos(linesPhoto);
		filereaderService.deleteAllFilereaders(linesFiles);
	}
	
	@Override
	@Transactional
	public void deletePosts(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) postRepository.countPosts(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			removePosts(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPosts(final Long companyId) {
		final List<UUID> linesUUID = postRepository.findAllTrashedIds(companyId);
		if(!linesUUID.isEmpty()) {
			removePosts(linesUUID);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UUIDMini> findAllCategory(final Long companyId) {
		return categoryRepository.findAllCategoryMini(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPromotesList(final Long companyId, final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = promoteRepository.countAllRecyclePromoteCriteria(companyId, search);
		final List<RecyclePromoteLine> lines = countResult == 0L ? new ArrayList<RecyclePromoteLine>() 
				: promoteRepository.findAllRecyclePromoteCriteria(companyId, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Promote restorePromote(final String id, final Long companyId) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Promote promote = uOptional.get();
			promote.setHasTrashed(false);
			return promoteRepository.save(promote);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Promote deletePromote(final String id, final Long companyId) {
		try {
			final Optional<Promote> uOptional = promoteRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Promote promote = uOptional.get();
			final UUID promoteId = promote.getId();
			if(promote.getPhotoUUID() != null) {
				photoService.deletePhoto(promote.getPhotoUUID());
			}
			promoteActivityRepository.deleteByPromoteUUID(promoteId);
			promoteWilayaRepository.deleteByPromoteUUID(promoteId);
			promoteRepository.delete(promote);
			return promote;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final void removePromotes(final List<UUID> linesUUID) {
		final List<UUID> linesPhoto = promoteRepository.findAllPhotoUUIDById(linesUUID);
		promoteActivityRepository.deletePromoteActivityByPromoteIds(linesUUID);
		promoteWilayaRepository.deletePromoteWilayaByPromoteIds(linesUUID);
		promoteRepository.deletePromotes(linesUUID);
		if(!linesPhoto.isEmpty()) {
			photoService.deleteAllPhotos(linesPhoto);
		}
	}
	
	@Override
	@Transactional
	public void deletePromotes(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) promoteRepository.countPromotes(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			removePromotes(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPromotes(final Long companyId) {
		final List<UUID> linesUUID = promoteRepository.findAllTrashedIds(companyId);
		if(!linesUUID.isEmpty()) {
			removePromotes(linesUUID);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAnnoncesList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = annonceRepository.countAllRecycleAnnonceCriteria(companyId, filter, search);
		final List<RecycleAnnonceLine> lines = countResult == 0L ? new ArrayList<RecycleAnnonceLine>() 
				: annonceRepository.findAllRecycleAnnonceCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Annonce restoreAnnonce(final String id, final Long companyId) {
		try {
			final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Annonce annonce = uOptional.get();
			annonce.setHasTrashed(false);
			return annonceRepository.save(annonce);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Annonce deleteAnnonce(final String id, final Long companyId) {
		try {
			final Optional<Annonce> uOptional = annonceRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Annonce annonce = uOptional.get();
			final UUID annonceId = annonce.getId();
			final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentId(annonceId, DocumentType.annonce);
			final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentId(annonceId, DocumentType.annonce);
			annonceDetailRepository.deleteById(annonceId);
			annonceActivityRepository.deleteByAnnonceUUID(annonceId);
			annonceWilayaRepository.deleteByAnnonceUUID(annonceId);
			annonceRepository.delete(annonce);
			guestDocumentRepository.deleteByDocumentIdAndType(annonceId, DocumentType.annonce);
			favoriteDocumentRepository.deleteByDocumentIdAndType(annonceId, DocumentType.annonce);
			campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
			campaignRepository.deleteByDocumentIdAndType(annonceId, DocumentType.annonce);
			filereaderService.deleteAllFilereaders(linesFiles);
			return annonce;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final void removeAnnonces(final List<UUID> linesUUID) {
		final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentIds(linesUUID, DocumentType.annonce);
		final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentIds(linesUUID, DocumentType.annonce);
		annonceDetailRepository.deleteAnnonceDetails(linesUUID);
		annonceActivityRepository.deleteAnnonceActivityByAnnonceIds(linesUUID);
		annonceWilayaRepository.deleteAnnonceWilayaByAnnonceIds(linesUUID);
		annonceRepository.deleteAnnonces(linesUUID);
		guestDocumentRepository.deleteGuestDocumentByDocumentsIds(linesUUID, DocumentType.annonce);
		favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(linesUUID, DocumentType.annonce);
		campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
		campaignRepository.deleteCampaignsByDocumentsIds(linesUUID, DocumentType.annonce);
		filereaderService.deleteAllFilereaders(linesFiles);
	}
	
	@Override
	@Transactional
	public void deleteAnnonces(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) annonceRepository.countAnnonces(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			removeAnnonces(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllAnnonces(final Long companyId) {
		final List<UUID> linesUUID = annonceRepository.findAllTrashedIds(companyId);
		if(!linesUUID.isEmpty()) {
			removeAnnonces(linesUUID);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEmployesList(final Long companyId, final Integer filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = employeRepository.countAllRecycleEmployeCriteria(companyId, filter, search);
		final List<RecycleEmployeLine> lines = countResult == 0L ? new ArrayList<RecycleEmployeLine>() 
				: employeRepository.findAllRecycleEmployeCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Employe restoreEmploye(final String id, final Long companyId) {
		try {
			final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Employe employe = uOptional.get();
			employe.setHasTrashed(false);
			return employeRepository.save(employe);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public Employe deleteEmploye(final String id, final Long companyId) {
		try {
			final Optional<Employe> uOptional = employeRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Employe employe = uOptional.get();
			final UUID employeId = employe.getId();
			final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentId(employeId, DocumentType.employe);
			employeDetailRepository.deleteById(employeId);
			employeLocationRepository.deleteByEmployeUUID(employeId);
			employeRepository.delete(employe);
			guestDocumentRepository.deleteByDocumentIdAndType(employeId, DocumentType.employe);
			favoriteDocumentRepository.deleteByDocumentIdAndType(employeId, DocumentType.employe);
			filereaderService.deleteAllFilereaders(linesFiles);
			return employe;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final void removeEmployes(final List<UUID> linesUUID) {
		final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentIds(linesUUID, DocumentType.employe);
		employeDetailRepository.deleteEmployeDetails(linesUUID);
		employeLocationRepository.deleteEmployeLocationByEmployeIds(linesUUID);
		employeRepository.deleteEmployes(linesUUID);
		guestDocumentRepository.deleteGuestDocumentByDocumentsIds(linesUUID, DocumentType.employe);
		favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(linesUUID, DocumentType.employe);
		filereaderService.deleteAllFilereaders(linesFiles);
	}
	
	@Override
	@Transactional
	public void deleteEmployes(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) employeRepository.countEmployes(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			removeEmployes(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllEmployes(final Long companyId) {
		final List<UUID> linesUUID = employeRepository.findAllTrashedIds(companyId);
		if(!linesUUID.isEmpty()) {
			removeEmployes(linesUUID);
		}
	}
	
}
