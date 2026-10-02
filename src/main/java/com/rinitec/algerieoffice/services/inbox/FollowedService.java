package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteAccountRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedAccount;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedCompany;
import com.rinitec.algerieoffice.web.modal.inbox.FollowedUser;

@Service
public class FollowedService implements IFollowedService {

	private UserRepository userRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteAccountRepository favoriteAccountRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public FollowedService(UserRepository userRepository, FavoriteCompanyRepository favoriteCompanyRepository, 
			FavoriteAccountRepository favoriteAccountRepository, ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteAccountRepository = favoriteAccountRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FollowedCompany> findAllFollowedCompany(final Long userId, final String search, final int page, final int rows) {
		return favoriteCompanyRepository.findAllFollowedCompany(userId, search, page, rows);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FollowedAccount> findAllFollowedAccount(final Long userId, final String search, final int page, final int rows) {
		final List<FollowedAccount> lines = favoriteAccountRepository.findAllFollowedAccount(userId, search, page, rows);
		if(!lines.isEmpty()) {
			for (final FollowedAccount line : lines) {
				final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
				line.updateOnline(hasOnline);
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FollowedUser> findAllFollowedUser(final Long userId, final Long companyId, final String search, final int page, final int rows) {
		final List<FollowedUser> lines = userRepository.findAllFollowedUser(userId, companyId, search, page, rows);
		if(!lines.isEmpty()) {
			for (final FollowedUser line : lines) {
				final boolean hasOnline = activeUserStore.hasLogged(line.getEmail());
				line.updateOnline(hasOnline);
			}
		}
		return lines;
	}
	
}
