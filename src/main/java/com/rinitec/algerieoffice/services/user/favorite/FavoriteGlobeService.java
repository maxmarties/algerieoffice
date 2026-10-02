package com.rinitec.algerieoffice.services.user.favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteAccountLine;
import com.rinitec.algerieoffice.web.modal.user.globe.FavoriteCompanyLine;

@Service
public class FavoriteGlobeService implements IFavoriteGlobeService {

	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	
	@Autowired
	public FavoriteGlobeService(FavoriteCompanyRepository favoriteCompanyRepository, FavoriteAccountRepository favoriteAccountRepository) {
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoriteCompanyList(final Long userId, final Integer filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = favoriteCompanyRepository.countAllFavoriteCompanyCriteria(userId, filter, search);
		final List<FavoriteCompanyLine> lines = countResult == 0L ? new ArrayList<FavoriteCompanyLine>() 
				: favoriteCompanyRepository.findAllFavoriteCompanyCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void alertFavoriteCompany(final String id, final Long userId) {
		try {
			final Optional<FavoriteCompany> uOptional = favoriteCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final FavoriteCompany favoriteCompany = uOptional.get();
			favoriteCompany.setAlert(!favoriteCompany.isAlert());
			favoriteCompanyRepository.save(favoriteCompany);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFavoriteCompany(final String id, final Long userId) {
		try {
			final Optional<FavoriteCompany> uOptional = favoriteCompanyRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final FavoriteCompany favoriteCompany = uOptional.get();
			favoriteCompanyRepository.delete(favoriteCompany);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFavoriteCompanies(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) favoriteCompanyRepository.countFavoriteCompanies(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			favoriteCompanyRepository.deleteFavoriteCompanies(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllFavoriteCompanies(final Long userId) {
		favoriteCompanyRepository.deleteByUserId(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasFavoriteAccount(final Long userId, final Long accountId) {
		return favoriteAccountRepository.existsByUserIdAndAccountId(userId, accountId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findFavoriteAccountList(final Long userId, final String filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = favoriteAccountRepository.countAllFavoriteAccountCriteria(userId, filter, search);
		final List<FavoriteAccountLine> lines = countResult == 0L ? new ArrayList<FavoriteAccountLine>() 
				: favoriteAccountRepository.findAllFavoriteAccountCriteria(userId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public void alertFavoriteAccount(final String id, final Long userId) {
		try {
			final Optional<FavoriteAccount> uOptional = favoriteAccountRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final FavoriteAccount favoriteAccount = uOptional.get();
			favoriteAccount.setAlert(!favoriteAccount.isAlert());
			favoriteAccountRepository.save(favoriteAccount);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFavoriteAccount(final String id, final Long userId) {
		try {
			final Optional<FavoriteAccount> uOptional = favoriteAccountRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !userId.equals(uOptional.get().getUserId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final FavoriteAccount favoriteAccount = uOptional.get();
			favoriteAccountRepository.delete(favoriteAccount);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteFavoriteAccounts(final List<String> lines, final Long userId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) favoriteAccountRepository.countFavoriteAccounts(userId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			favoriteAccountRepository.deleteFavoriteAccounts(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllFavoriteAccounts(final Long userId) {
		favoriteAccountRepository.deleteByUserId(userId);
	}
	
}
