package com.rinitec.algerieoffice.services.admins.premium;

import com.rinitec.algerieoffice.web.modal.premium.PremiumPost;

public interface IPremiumService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasPremium(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasPremiumRegular(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	int getMaxKeysword(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param countPost
	 * @return
	 */
	PremiumPost parsePremiumPost(Long companyId, Long countPost);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param countProduct
	 * @return
	 */
	boolean hasMaxProduct(Long companyId, Long countProduct);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param countUser
	 * @return
	 */
	boolean hasMaxUser(Long companyId, Long countUser);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param countAgent
	 * @return
	 */
	boolean hasMaxAgent(Long companyId, Long countAgent);
	
}
