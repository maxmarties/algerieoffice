package com.rinitec.algerieoffice.services.user.favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteAnnonceLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEmployeLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoriteEventLine;
import com.rinitec.algerieoffice.web.modal.user.favorite.FavoritePostLine;

@Service
public class FavoriteDocumentService implements IFavoriteDocumentService {

	private FavoriteDocumentRepository favoriteDocumentRepository;
	
	@Autowired
	public FavoriteDocumentService(FavoriteDocumentRepository favoriteDocumentRepository) {
		this.favoriteDocumentRepository = favoriteDocumentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasFavoriteDocument(final Long userId, final String documentId, final DocumentType type) {
		try {
			return favoriteDocumentRepository.existsByUserIdAndDocumentIdAndType(userId, UUID.fromString(documentId), type);
		} catch (IllegalArgumentException e) {}
		return false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoritePostList(final Long userId, final String filter, final String search, final int sort, final int rows, 
			final int page, final boolean hasDesc) {
		final Long countResult = favoriteDocumentRepository.countAllFavoritePostCriteria(userId, filter, search);
		final List<FavoritePostLine> lines = countResult == 0L ? new ArrayList<FavoritePostLine>() 
				: favoriteDocumentRepository.findAllFavoritePostCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoriteAnnonceList(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = favoriteDocumentRepository.countAllFavoriteAnnonceCriteria(userId, filter, search);
		final List<FavoriteAnnonceLine> lines = countResult == 0L ? new ArrayList<FavoriteAnnonceLine>() 
				: favoriteDocumentRepository.findAllFavoriteAnnonceCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoriteEventList(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = favoriteDocumentRepository.countAllFavoriteEventCriteria(userId, filter, search);
		final List<FavoriteEventLine> lines = countResult == 0L ? new ArrayList<FavoriteEventLine>() 
				: favoriteDocumentRepository.findAllFavoriteEventCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoriteEmployetList(final Long userId, final Integer filter, final String search, final int sort,
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = favoriteDocumentRepository.countAllFavoriteEmployeCriteria(userId, filter, search);
		final List<FavoriteEmployeLine> lines = countResult == 0L ? new ArrayList<FavoriteEmployeLine>() 
				: favoriteDocumentRepository.findAllFavoriteEmployeCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void deleteFavoriteDocument(final String id, final Long userId, final DocumentType type) {
		try {
			final Optional<FavoriteDocument> uOptional = favoriteDocumentRepository.findById(UUID.fromString(id));
			final FavoriteDocument favoriteDocument = uOptional.isPresent() ? uOptional.get() : null;
			if(favoriteDocument == null || !favoriteDocument.getUserId().equals(userId) 
					|| !favoriteDocument.getType().equals(type)) {
				throw new NotFoundException("message.error.notfound");
			}
			favoriteDocumentRepository.delete(favoriteDocument);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFavoriteDocuments(final List<String> lines, final Long userId, final DocumentType type) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) favoriteDocumentRepository.countFavoriteDocuments(userId, linesUUID, type)) {
				throw new NotFoundException("message.error.notfound");
			}
			favoriteDocumentRepository.deleteFavoriteDocuments(linesUUID, type);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllFavoriteDocuments(final Long userId, final DocumentType type) {
		favoriteDocumentRepository.deleteAllFavoriteDocuments(userId, type);
	}
	
}
