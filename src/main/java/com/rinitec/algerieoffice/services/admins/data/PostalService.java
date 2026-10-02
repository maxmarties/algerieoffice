package com.rinitec.algerieoffice.services.admins.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.data.OrderPostalRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.OrderPostal;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.data.PostalLine;

@Service
public class PostalService implements IPostalService {

	private OrderPostalRepository orderPostalRepository;
	
	@Autowired
	public PostalService(OrderPostalRepository orderPostalRepository) {
		this.orderPostalRepository = orderPostalRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPostalList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = orderPostalRepository.countAllPostal(search);
		final List<PostalLine> lines = countResult == 0L ? new ArrayList<PostalLine>() 
				: orderPostalRepository.findAllPostal(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public OrderPostal deletePostal(final String id) {
		try {
			final Optional<OrderPostal> uOptional = orderPostalRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final OrderPostal orderPostal = uOptional.get();
			orderPostalRepository.delete(orderPostal);
			return orderPostal;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deletePostals(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) orderPostalRepository.countPostal(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			orderPostalRepository.deletePostal(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
