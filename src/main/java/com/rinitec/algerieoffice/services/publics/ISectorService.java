package com.rinitec.algerieoffice.services.publics;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.modal.publics.sectors.ActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorLink;

public interface ISectorService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<ActivityLink> findActivityLink();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @return
	 */
	List<SectorLink> findSectorLinkList(int sector);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	Activity findActivityByURL(String url);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<SectorActivityLink> findWilayaLinkList();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param activity
	 * @return
	 */
	List<SectorActivityLink> findSectorActivityLinkList(Activity activity);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param wilaya
	 * @return
	 */
	List<SectorActivityLink> findWilayaSectorLinkList(int wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param wilaya
	 * @param sector
	 * @return
	 */
	List<SectorLink> findWilayaActivityLinkList(int wilaya, int sector);
	
}
