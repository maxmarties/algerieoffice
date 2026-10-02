package com.rinitec.algerieoffice.persistence.dao.admins.data;

import java.util.List;

import javax.persistence.Tuple;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.modal.publics.sectors.ActivityLink;
import com.rinitec.algerieoffice.web.modal.publics.sectors.SectorLink;

public interface ActivityRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllActivityCriteria(String filter, String search);

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<Activity> findAllActivityCriteria(String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<List<String>> findAllChoseActivityCriteria();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	List<ActivityLink> findActivityLinkCriteria();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @return
	 */
	List<SectorLink> findSectorLinkCriteria(int sector);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param wilaya
	 * @param sector
	 * @return
	 */
	List<Tuple> findWilayaActivityLinkCriteria(int wilaya, int sector);
	
}
