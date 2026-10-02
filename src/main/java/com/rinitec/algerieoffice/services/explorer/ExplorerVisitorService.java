package com.rinitec.algerieoffice.services.explorer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteCompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.feedback.EvaluationRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.CurrentVisitor;

@Service
public class ExplorerVisitorService implements IExplorerVisitorService {

	private UserRepository userRepository;
	private ProfileRepository profileRepository;
	private EvaluationRepository evaluationRepository;
	private FavoriteCompanyRepository favoriteCompanyRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private ActiveUserStore activeUserStore;
	
	@Autowired
	public ExplorerVisitorService(UserRepository userRepository, ProfileRepository profileRepository, EvaluationRepository evaluationRepository, 
			FavoriteCompanyRepository favoriteCompanyRepository, FavoriteDocumentRepository favoriteDocumentRepository, ActiveUserStore activeUserStore) {
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
		this.evaluationRepository = evaluationRepository;
		this.favoriteCompanyRepository = favoriteCompanyRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.activeUserStore = activeUserStore;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasCompanyLogin(final Long companyId) {
		final List<String> emails = userRepository.findAllEmailByCompanyId(companyId);
		for (final String email : emails) {
			if(activeUserStore.hasLogged(email)) {
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = true)
	private final Integer readFavoriteCompany(final Long userId, final Long companyId) {
		final Optional<Integer> uOptional = favoriteCompanyRepository.findTypeByUserAndCompany(userId, companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CurrentVisitor readCurrentVisitor(final User user, final Long companyId) {
		final Long userId = user.getId();
		final boolean hasLeader = companyId.equals(user.getCompanyId());
		final Integer favorite = !hasLeader ? readFavoriteCompany(userId, companyId) : null;
		final Evaluation evaluation = !hasLeader ? evaluationRepository.findByUserIdAndCompanyId(userId, companyId) : null;
		return new CurrentVisitor(hasLeader, favorite, evaluation);
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
	public boolean hasAutorizedAnnonce(final CurrentUser currentUser, final Long companyId, final Integer visibility) {
		switch(visibility) {
		case 2: return currentUser != null;
		case 3: return currentUser != null && currentUser.hasCompany();
		case 4: return false;//TODO COMPANY SECTOR
		case 5: return currentUser != null && readFavoriteCompany(currentUser.getUserId(), companyId) != null;
		default: return true;
		}
	}
	
	private final ExplorerContactForm parseExplorerContactForm(final User user, final Profile profile, final Long companyId) {
		final ExplorerContactForm explorerContactForm = new ExplorerContactForm(companyId);
		explorerContactForm.setPro(user.getCompanyId() != null);
		explorerContactForm.setFirstname(user.getFirstName());
		explorerContactForm.setLastname(user.getLastName());
		explorerContactForm.setEmail(user.getEmail());
		if(profile != null) {
			explorerContactForm.setSexe(profile.getSexe());
			explorerContactForm.setFunction(profile.getFunction());
			explorerContactForm.setPhone(profile.getPhone());
			explorerContactForm.setPostal(profile.getPostal());
		}
		return explorerContactForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExplorerContactForm readExplorerContactForm(final CurrentUser currentUser, final Long companyId) {
		if(currentUser != null) {
			final User user = currentUser.getUser();
			final Optional<Profile> uOptional = profileRepository.findById(user.getId());
			return parseExplorerContactForm(user, uOptional.isPresent() ? uOptional.get() : null, companyId);
		}
		return new ExplorerContactForm(companyId);
	}
	
	private final DocumentContactForm parseDocumentContactForm(final User user, final String phone, final Long companyId, 
			final String documentId, final DocumentType type) {
		final DocumentContactForm documentContactForm = new DocumentContactForm();
		documentContactForm.setCompanyId(companyId);
		documentContactForm.setDocumentId(documentId);
		documentContactForm.setType(type);
		documentContactForm.setName(user.getDisplayName());
		documentContactForm.setPhone(phone);
		documentContactForm.setEmail(user.getEmail());
		return documentContactForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DocumentContactForm readDocumentContactForm(final CurrentUser currentUser, final Long companyId, 
			final String documentId, final DocumentType type) {
		if(currentUser != null) {
			final User user = currentUser.getUser();
			final Optional<String> uOptional = profileRepository.findPhoneByUserId(user.getId());
			return parseDocumentContactForm(user, uOptional.isPresent() ? uOptional.get() : null, companyId, documentId, type);
		}
		return new DocumentContactForm(companyId, documentId, type);
	}
	
}
