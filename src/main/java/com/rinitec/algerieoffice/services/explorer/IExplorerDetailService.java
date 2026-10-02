package com.rinitec.algerieoffice.services.explorer;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEvent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePost;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageWork;

public interface IExplorerDetailService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param identify
	 * @return
	 */
	ExplorerPageEvent readExplorerPageEvent(ExplorerCurrent explorerCurrent, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param identify
	 * @return
	 */
	ExplorerPageWork readExplorerPageWork(ExplorerCurrent explorerCurrent, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param identify
	 * @return
	 */
	ExplorerPagePost readExplorerPagePost(ExplorerCurrent explorerCurrent, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param identify
	 * @return
	 */
	ExplorerPageAnnonce readExplorerPageAnnonce(ExplorerCurrent explorerCurrent, String identify);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param explorerCurrent
	 * @param identify
	 * @return
	 */
	ExplorerPageEmploye readExplorerPageEmploye(ExplorerCurrent explorerCurrent, String identify);
	
}
