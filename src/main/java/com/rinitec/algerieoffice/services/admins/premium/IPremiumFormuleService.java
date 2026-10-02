package com.rinitec.algerieoffice.services.admins.premium;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.web.form.admins.datas.SocialFooterForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PricePremiumForm;

public interface IPremiumFormuleService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	SocialFooterForm readSocialFooterForm();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param socialFooterForm
	 * @return
	 */
	PremiumFormule updateSocialFooter(SocialFooterForm socialFooterForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	PricePremiumForm readPricePremiumForm();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pricePremiumForm
	 * @return
	 */
	PremiumFormule updatePricePremium(PricePremiumForm pricePremiumForm);
	
}
