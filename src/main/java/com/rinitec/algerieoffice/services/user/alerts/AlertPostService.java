package com.rinitec.algerieoffice.services.user.alerts;

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

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.users.alerts.AlertPostRepository;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.user.alerts.AlertPostForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostLine;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

@Service
public class AlertPostService implements IAlertPostService {

	private AlertPostRepository alertPostRepository;
	
	@Autowired
	public AlertPostService(AlertPostRepository alertPostRepository) {
		this.alertPostRepository = alertPostRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AlertPostForm readAlertPostForm(final String id, final Long userId, final DocumentType type) {
		try {
			final Optional<AlertPost> uOptional = alertPostRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && userId.equals(uOptional.get().getUserId()) 
					&& type.equals(uOptional.get().getType())) {
				final AlertPost alertPost = uOptional.get();
				final AlertPostForm alertPostForm = new AlertPostForm();
				alertPostForm.setId(id);
				alertPostForm.setUserId(userId);
				alertPostForm.setName(alertPost.getName());
				alertPostForm.setSector(alertPost.getSector());
				alertPostForm.setWilaya(alertPost.getWilaya());
				alertPostForm.setFrequency(alertPost.getFrequency());
				alertPostForm.setType(type);
				alertPostForm.setEnabled(alertPost.isEnabled());
				return alertPostForm;
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Override
	@Transactional
	public AlertPost addAlertPost(final AlertPostForm alertPostForm) {
		final AlertPost alertPost = new AlertPost();
		alertPost.setUserId(alertPostForm.getUserId());
		alertPost.setName(alertPostForm.getName());
		alertPost.setSector(alertPostForm.getSector());
		alertPost.setWilaya(alertPostForm.getWilaya());
		alertPost.setFrequency(alertPostForm.getFrequency());
		alertPost.setPostedDate(new DateTime(Date.from(Instant.now())));
		alertPost.setType(alertPostForm.getType());
		alertPost.setEnabled(alertPostForm.isEnabled());
		return alertPostRepository.save(alertPost);
	}
	
	@Override
	@Transactional
	public AlertPost updateAlertPost(final AlertPostForm alertPostForm) {
		final Optional<AlertPost> uOptional = alertPostRepository.findById(UUID.fromString(alertPostForm.getId()));
		if(uOptional.isPresent() && alertPostForm.getUserId().equals(uOptional.get().getUserId()) 
				&& alertPostForm.getType().equals(uOptional.get().getType())) {
			final AlertPost alertPost = uOptional.get();
			alertPost.setName(alertPostForm.getName());
			alertPost.setSector(alertPostForm.getSector());
			alertPost.setWilaya(alertPostForm.getWilaya());
			alertPost.setFrequency(alertPostForm.getFrequency());
			alertPost.setEnabled(alertPostForm.isEnabled());
			return alertPostRepository.save(alertPost);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAlertPostList(final Long userId, final Integer filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc, final DocumentType type) {
		final Long countResult = alertPostRepository.countAllAlertPostCriteria(userId, filter, search, type);
		final List<AlertPostLine> lines = countResult == 0L ? new ArrayList<AlertPostLine>() 
				: alertPostRepository.findAllAlertPostCriteria(userId, filter, search, sort, rows, page, hasDesc, type);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteAlertPost(final String id, final Long userId, final DocumentType type) {
		try {
			final Optional<AlertPost> uOptional = alertPostRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId()) 
					|| !type.equals(uOptional.get().getType())) {
				throw new NotFoundException("message.error.notfound");
			}
			final AlertPost alertPost = uOptional.get();
			alertPostRepository.delete(alertPost);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAlertPosts(final List<String> lines, final Long userId, final DocumentType type) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) alertPostRepository.countAlertPosts(userId, linesUUID, type)) {
				throw new NotFoundException("message.error.notfound");
			}
			alertPostRepository.deleteAlertPosts(linesUUID, type);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllAlertPosts(final Long userId, final DocumentType type) {
		alertPostRepository.deleteAllAlertPosts(userId, type);
	}
	
	@Transactional
	private final void incrementAlertPotentiel(final List<AlertPostResult> lines) {
		if(!lines.isEmpty()) {
			final List<UUID> linesId = new ArrayList<UUID>();
			for (final AlertPostResult line : lines) {
				linesId.add(line.getId());
			}
			alertPostRepository.incrementAlertPotentiel(linesId);
		}
	}
	
	@Override
	@Transactional
	public List<AlertPostResult> findLastAlertPost(final DocumentType type, final int day, final int limit) {
		final List<AlertPostResult> lines = alertPostRepository.findLastAlertPost(type, day, limit);
		incrementAlertPotentiel(lines);
		return lines;
	}
	
}
