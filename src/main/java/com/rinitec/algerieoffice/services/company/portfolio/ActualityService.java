package com.rinitec.algerieoffice.services.company.portfolio;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityCommentRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityLikeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.CommentLikeRepository;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.ActualityForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.portfolio.ActualityLine;

@Service
public class ActualityService implements IActualityService {

	private ActualityRepository actualityRepository;
	private ActualityLikeRepository actualityLikeRepository;
	private ActualityCommentRepository actualityCommentRepository;
	private CommentLikeRepository commentLikeRepository;
	private IPhotoService photoService;
	
	@Autowired
	public ActualityService(ActualityRepository actualityRepository, ActualityLikeRepository actualityLikeRepository, 
			ActualityCommentRepository actualityCommentRepository, CommentLikeRepository commentLikeRepository, IPhotoService photoService) {
		this.actualityRepository = actualityRepository;
		this.actualityLikeRepository = actualityLikeRepository;
		this.actualityCommentRepository = actualityCommentRepository;
		this.commentLikeRepository = commentLikeRepository;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countActuality(final Long companyId) {
		return actualityRepository.countByCompanyId(companyId);
	}
	
	private final ActualityForm parseActualityForm(final Actuality actuality) {
		final ActualityForm actualityForm = new ActualityForm();
		actualityForm.setId(actuality.getId().toString());
		actualityForm.setCompanyId(actuality.getCompanyId());
		actualityForm.setActuDate(DateTimeFormat.forPattern("dd/MM/yyyy").print(actuality.getActuDate()));
		actualityForm.setTitle(actuality.getTitle());
		actualityForm.setDescription(actuality.getDescription());
		actualityForm.setUrlExtern(actuality.getUrlExtern());
		actualityForm.setHasPublished(actuality.getHasPublished());
		actualityForm.setHasAvatar(true);
		actualityForm.setUrlAvatar(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(actuality.getPhotoUUID().toString()));
		return actualityForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ActualityForm readActualityForm(final String id, final Long companyId) {
		try {
			final Optional<Actuality> uOptional = actualityRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Actuality actuality = uOptional.get();
				return parseActualityForm(actuality);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Actuality postActuality(final Actuality actuality, final ActualityForm actualityForm, final UUID photoUUID, final Long userId) {
		actuality.setActuDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(actualityForm.getActuDate()));
		actuality.setTitle(actualityForm.getTitle());
		actuality.setDescription(actualityForm.getDescription());
		actuality.setUrlExtern(!StringUtils.isEmpty(actualityForm.getUrlExtern()) ? actualityForm.getUrlExtern() : null);
		actuality.setPhotoUUID(photoUUID);
		actuality.setModifiedDate(new DateTime(Date.from(Instant.now())));
		actuality.setSharedDate(new DateTime(Date.from(Instant.now())));
		actuality.setAutorId(userId);
		actuality.setHasPublished(actualityForm.getHasPublished());
		return actualityRepository.save(actuality);
	}
	
	@Override
	@Transactional
	public Actuality addActuality(final ActualityForm actualityForm, final Long userId) {
		if(!actualityForm.isHasAvatar()) {
			throw new InvalidImageException("message.input.required");
		}
		if(!StringUtils.isEmpty(actualityForm.getUrlExtern()) && actualityRepository.existsByUrlExtern(actualityForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Photo photo = photoService.addPhoto(actualityForm.getFile(), actualityForm.getCompanyId(), PhotoType.actu);
		return postActuality(new Actuality(actualityForm.getCompanyId()), actualityForm, photo.getId(), userId);
	}
	
	@Override
	@Transactional
	public Actuality updateActuality(final ActualityForm actualityForm, final Long userId) {
		final Optional<Actuality> uOptional = actualityRepository.findById(UUID.fromString(actualityForm.getId()));
		if(uOptional.isPresent() && actualityForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Actuality actuality = uOptional.get();
			if(!actualityForm.isHasAvatar()) {
				throw new InvalidImageException("message.input.required");
			}
			if(!StringUtils.isEmpty(actualityForm.getUrlExtern()) && !actualityForm.getUrlExtern().equalsIgnoreCase(actuality.getUrlExtern()) 
					&& actualityRepository.existsByUrlExtern(actualityForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			if(actualityForm.isHasFileChanged()) {
				photoService.updatePhoto(actuality.getPhotoUUID(), actualityForm.getFile());
			}
			return postActuality(actuality, actualityForm, actuality.getPhotoUUID(), userId);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findActualitiesList(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = actualityRepository.countAllActualityCriteria(companyId, filter, search);
		final List<ActualityLine> lines = countResult == 0L ? new ArrayList<ActualityLine>() 
				: actualityRepository.findAllActualityCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutorActuality(final Long companyId) {
		return actualityRepository.findAllAutorCriteria(companyId);
	}
	
	@Override
	@Transactional
	public Actuality deleteActuality(final String id, final Long companyId) {
		try {
			final Optional<Actuality> uOptional = actualityRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Actuality actuality = uOptional.get();
			final UUID actualityId = actuality.getId();
			final List<UUID> commentsUUID = actualityCommentRepository.findAllCommentUUIDByActualityId(actualityId);
			actualityLikeRepository.deleteByActualityId(actualityId);
			actualityCommentRepository.deleteByActualityId(actualityId);
			actualityRepository.delete(actuality);
			if(!commentsUUID.isEmpty()) {
				commentLikeRepository.deleteCommentsLike(commentsUUID);
			}
			photoService.deletePhoto(actualityId);
			return actuality;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteActualities(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) actualityRepository.countActualities(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesPhoto = actualityRepository.findAllPhotoUUIDById(linesUUID);
			final List<UUID> commentsUUID = actualityCommentRepository.findAllCommentUUIDByActualitiesId(linesUUID);
			actualityRepository.deleteActualities(linesUUID);
			actualityLikeRepository.deleteActualitiesLike(linesUUID);
			actualityCommentRepository.deleteActualitiesComment(linesUUID);
			if(!commentsUUID.isEmpty()) {
				commentLikeRepository.deleteCommentsLike(commentsUUID);
			}
			photoService.deleteAllPhotos(linesPhoto);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllActualities(final Long companyId) {
		final List<UUID> linesUUID = actualityRepository.findAllIDByCompanyId(companyId);
		if(!linesUUID.isEmpty()) {
			final List<UUID> linesPhoto = actualityRepository.findAllPhotoUUIDByCompanyId(companyId);
			final List<UUID> commentsUUID = actualityCommentRepository.findAllCommentUUIDByActualitiesId(linesUUID);
			actualityLikeRepository.deleteActualitiesLike(linesUUID);
			actualityCommentRepository.deleteActualitiesComment(linesUUID);
			actualityRepository.deleteByCompanyId(companyId);
			if(!commentsUUID.isEmpty()) {
				commentLikeRepository.deleteCommentsLike(commentsUUID);
			}
			photoService.deleteAllPhotos(linesPhoto);
		}
	}
	
}
