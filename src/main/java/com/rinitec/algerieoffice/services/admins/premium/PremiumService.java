package com.rinitec.algerieoffice.services.admins.premium;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.modal.premium.PremiumPost;

@Service
public class PremiumService implements IPremiumService {

	private PremiumRepository premiumRepository;
	
	@Autowired
	public PremiumService(PremiumRepository premiumRepository) {
		this.premiumRepository  = premiumRepository;
	}
	
	@Transactional(readOnly = true)
	private final int getPass(final Long companyId) {
		final Optional<Integer> uOptional = premiumRepository.findPremiumPassByCompanyId(companyId, new DateTime(Date.from(Instant.now())));
		return uOptional.isPresent() ? uOptional.get() : 0;
	}
	
	@Override
	public boolean hasPremium(final Long companyId) {
		return getPass(companyId) != 0;
	}
	
	@Override
	public boolean hasPremiumRegular(final Long companyId) {
		return getPass(companyId) > 1;
	}
	
	@Override
	public int getMaxKeysword(final Long companyId) {
		return ConstraintesForm.MAX_KEYSWORD[getPass(companyId)];
	}
	
	@Override
	public PremiumPost parsePremiumPost(final Long companyId, final Long countPost) {
		final int pass = getPass(companyId);
		return new PremiumPost(ConstraintesForm.MAX_KEYSWORD[pass], countPost >= ConstraintesForm.MAX_COUNT_POST[pass]);
	}
	
	@Override
	public boolean hasMaxProduct(final Long companyId, final Long countProduct) {
		return countProduct >= ConstraintesForm.MAX_COUNT_POST[getPass(companyId)];
	}
	
	@Override
	public boolean hasMaxUser(final Long companyId, final Long countUser) {
		return countUser >= ConstraintesForm.MAX_COUNT_USER[getPass(companyId)];
	}
	
	@Override
	public boolean hasMaxAgent(final Long companyId, final Long countAgent) {
		return countAgent >= ConstraintesForm.MAX_COUNT_AGENT[getPass(companyId)];
	}
	
}
