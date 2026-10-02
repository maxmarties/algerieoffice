package com.rinitec.algerieoffice.services.admins.premium;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumFormuleRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.admins.datas.SocialFooterForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PricePremiumForm;

@Service
public class PremiumFormuleService implements IPremiumFormuleService {

	private PremiumFormuleRepository premiumFormuleRepository;
	
	@Autowired
	public PremiumFormuleService(PremiumFormuleRepository premiumFormuleRepository) {
		this.premiumFormuleRepository = premiumFormuleRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SocialFooterForm readSocialFooterForm() {
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.SOCIALFOOTER_FORMULE);
		return new SocialFooterForm(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional
	public PremiumFormule updateSocialFooter(final SocialFooterForm socialFooterForm) {
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.SOCIALFOOTER_FORMULE);
		final PremiumFormule premiumFormule = uOptional.isPresent() ? uOptional.get() : new PremiumFormule(ConstraintesForm.SOCIALFOOTER_FORMULE);
		premiumFormule.setStart(socialFooterForm.getFacebook());
		premiumFormule.setMedium(socialFooterForm.getTwitter());
		premiumFormule.setPro(socialFooterForm.getLinkedin());
		premiumFormule.setExpert(socialFooterForm.getYoutube());
		premiumFormule.setBegginer(socialFooterForm.isBegginer());
		premiumFormule.setPromoted(socialFooterForm.isPromoted());
		premiumFormule.setSocialFrame(!StringUtils.isEmpty(socialFooterForm.getSocialForm()) ? socialFooterForm.getSocialForm() : null);
		return premiumFormuleRepository.save(premiumFormule);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PricePremiumForm readPricePremiumForm() {
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.PRICEPREMIUM_FORMULE);
		return new PricePremiumForm(uOptional.isPresent() ? uOptional.get() : null);
	}
	
	@Override
	@Transactional
	public PremiumFormule updatePricePremium(final PricePremiumForm pricePremiumForm) {
		final Optional<PremiumFormule> uOptional = premiumFormuleRepository.findById(ConstraintesForm.PRICEPREMIUM_FORMULE);
		final PremiumFormule premiumFormule = uOptional.isPresent() ? uOptional.get() : new PremiumFormule(ConstraintesForm.PRICEPREMIUM_FORMULE);
		premiumFormule.setStart(pricePremiumForm.getStart());
		premiumFormule.setMedium(pricePremiumForm.getMedium());
		premiumFormule.setPro(pricePremiumForm.getPro());
		premiumFormule.setExpert(pricePremiumForm.getExpert());
		return premiumFormuleRepository.save(premiumFormule);
	}
	
}
