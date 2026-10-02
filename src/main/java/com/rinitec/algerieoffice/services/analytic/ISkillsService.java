package com.rinitec.algerieoffice.services.analytic;

import com.rinitec.algerieoffice.web.modal.analytic.OfficeSkills;

public interface ISkillsService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	OfficeSkills readOfficeSkills();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	OfficeSkills readMarketplaceSkills();
	
}
