package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.web.listener.events.OnChaterEvent;
import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;

public interface IChaterService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	Chater addChater(OnChaterEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ChaterPush> findAllChaterPublic(int page, int rows);
	
}
