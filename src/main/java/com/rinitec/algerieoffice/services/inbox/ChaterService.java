package com.rinitec.algerieoffice.services.inbox;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.inbox.ChaterRepository;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.web.listener.events.OnChaterEvent;
import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;

@Service
public class ChaterService implements IChaterService {

	private ChaterRepository chaterRepository;
	
	@Autowired
	public ChaterService(ChaterRepository chaterRepository) {
		this.chaterRepository = chaterRepository;
	}
	
	@Override
	@Transactional
	public Chater addChater(final OnChaterEvent event) {
		final Chater chater = new Chater();
		chater.setUserId(event.getUserId());
		chater.setMessage(event.parseMessage());
		chater.setChaterDate(new DateTime(Date.from(Instant.now())));
		return chaterRepository.save(chater);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ChaterPush> findAllChaterPublic(final int page, final int rows) {
		return chaterRepository.findAllChatterCriteria(page, rows);
	}
	
}
