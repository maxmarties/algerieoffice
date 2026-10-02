package com.rinitec.algerieoffice.services.company.overview;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainabout;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainheader;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainoverview;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;
import com.rinitec.algerieoffice.web.form.company.overview.MainaboutForm;
import com.rinitec.algerieoffice.web.form.company.overview.MaincatalogForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainheaderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainsliderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainthinkForm;
import com.rinitec.algerieoffice.web.form.company.overview.PresentationForm;
import com.rinitec.algerieoffice.web.form.company.overview.TimelineForm;

public interface IOverviewService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MainheaderForm readMainheaderForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param mainheaderForm
	 * @return
	 */
	Mainheader updateMainheader(MainheaderForm mainheaderForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	PresentationForm readPresentationForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param presentationForm
	 * @return
	 */
	Mainoverview updatePresentation(PresentationForm presentationForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MaincatalogForm readMaincatalogForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param maincatalogForm
	 * @return
	 */
	Maincatalog updateMaincatalog(MaincatalogForm maincatalogForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MainsliderForm readMainsliderForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param mainsliderForm
	 * @return
	 */
	Mainslider updateMainslider(MainsliderForm mainsliderForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	TimelineForm readTimelineForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param timelineForm
	 */
	void updateTimeline(TimelineForm timelineForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MainaboutForm readMainaboutForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param mainaboutForm
	 * @return
	 */
	Mainabout updateMainabout(MainaboutForm mainaboutForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	MainthinkForm readMainthinkForm(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param mainthinkForm
	 * @return
	 */
	Mainthink updateMainthink(MainthinkForm mainthinkForm);
	
}
