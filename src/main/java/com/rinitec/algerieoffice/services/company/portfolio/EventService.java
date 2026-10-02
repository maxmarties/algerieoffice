package com.rinitec.algerieoffice.services.company.portfolio;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.CampaignTargetRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventCalendarRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventDetailRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestDocumentRepository;
import com.rinitec.algerieoffice.persistence.dao.users.favorite.FavoriteDocumentRepository;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.EventForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.portfolio.EventLine;

@Service
public class EventService implements IEventService {

	private EventRepository eventRepository;
	private EventDetailRepository eventDetailRepository;
	private EventCalendarRepository eventCalendarRepository;
	private GuestDocumentRepository guestDocumentRepository;
	private FavoriteDocumentRepository favoriteDocumentRepository;
	private CampaignRepository campaignRepository;
	private CampaignTargetRepository campaignTargetRepository;
	private IFilereaderService filereaderService;
	private IPhotoService photoService;
	
	@Autowired
	public EventService(EventRepository eventRepository, EventDetailRepository eventDetailRepository, EventCalendarRepository eventCalendarRepository, 
			GuestDocumentRepository guestDocumentRepository, FavoriteDocumentRepository favoriteDocumentRepository, CampaignRepository campaignRepository, 
			CampaignTargetRepository campaignTargetRepository, IFilereaderService filereaderService, IPhotoService photoService) {
		this.eventRepository = eventRepository;
		this.eventDetailRepository = eventDetailRepository;
		this.eventCalendarRepository = eventCalendarRepository;
		this.guestDocumentRepository = guestDocumentRepository;
		this.favoriteDocumentRepository = favoriteDocumentRepository;
		this.campaignRepository = campaignRepository;
		this.campaignTargetRepository = campaignTargetRepository;
		this.filereaderService = filereaderService;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countEvent(final Long companyId) {
		return eventRepository.countByCompanyId(companyId);
	}
	
	private final EventForm parseEventForm(final Event event, final EventDetail eventDetail, final List<EventCalendar> eventsCalendar) {
		final EventForm eventForm = new EventForm();
		eventForm.setId(event.getId().toString());
		eventForm.setCompanyId(event.getCompanyId());
		eventForm.setTitle(event.getTitle());
		eventForm.setIdentify(event.getIdentify());
		eventForm.setDescription(eventDetail.getDescription());
		eventForm.setDetail(new String(eventDetail.getDetail()));
		eventForm.setKeysword(event.getKeysword());
		eventForm.setUrlExtern(event.getUrlExtern());
		eventForm.setHasPublished(event.getHasPublished());
		eventForm.setHasAvatar(true);
		eventForm.setUrlAvatar(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(eventDetail.getPhotoUUID().toString()));
		for (final EventCalendar eventCalendar : eventsCalendar) {
			eventForm.getIdents().add(eventCalendar.getId().toString());
			eventForm.getEventsDate().add(DateTimeFormat.forPattern("dd/MM/yyyy").print(eventCalendar.getEventDate()));
			eventForm.getClocksOpen().add(eventCalendar.getClockOpen());
			eventForm.getClocksClose().add(eventCalendar.getClockClose());
			eventForm.getWilayas().add(eventCalendar.getWilaya());
		}
		return eventForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public EventForm readEventForm(final String id, final Long companyId) {
		try {
			final Optional<Event> uOptional = eventRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Event event = uOptional.get();
				final EventDetail eventDetail = eventDetailRepository.findById(event.getId()).get();
				final List<EventCalendar> eventsCalendar = eventCalendarRepository.findAllByEventUUID(event.getId());
				return parseEventForm(event, eventDetail, eventsCalendar);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Event postEvent(final Event event, final EventForm eventForm, final Long userId) {
		event.setTitle(eventForm.getTitle());
		event.setIdentify(eventForm.getIdentify());
		event.setKeysword(eventForm.getKeysword());
		event.setUrlExtern(!StringUtils.isEmpty(eventForm.getUrlExtern()) ? eventForm.getUrlExtern() : null);
		event.setModifiedDate(new DateTime(Date.from(Instant.now())));
		event.setAutorId(userId);
		event.setHasPublished(eventForm.getHasPublished());
		return eventRepository.save(event);
	}
	
	@Transactional
	private final EventDetail postEventDetail(final EventDetail eventDetail, final EventForm eventForm, final UUID photoUUID) {
		eventDetail.setDescription(eventForm.getDescription());
		eventDetail.setDetail(eventForm.getDetail().getBytes());
		eventDetail.setPhotoUUID(photoUUID);
		return eventDetailRepository.save(eventDetail);
	}
	
	@Transactional
	private final void createEventsCalendar(final UUID eventId, final EventForm eventForm) {
		final List<EventCalendar> eventsCalendar = new ArrayList<EventCalendar>();
		for(int i = 0; i < eventForm.getEventsDate().size(); i++) {
			final EventCalendar eventCalendar = new EventCalendar();
			eventCalendar.setEventUUID(eventId);
			eventCalendar.setEventDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(eventForm.getEventsDate().get(i)));
			eventCalendar.setClockOpen(eventForm.getClocksOpen().get(i));
			eventCalendar.setClockClose(eventForm.getClocksClose().get(i));
			eventCalendar.setWilaya(eventForm.getWilayas().get(i));
			eventsCalendar.add(eventCalendar);
		}
		eventCalendarRepository.saveAll(eventsCalendar);
	}
	
	@Override
	@Transactional
	public Event addEvent(final EventForm eventForm, final int maxKeysword, final Long userId) {
		if(eventForm.getEventsDate().isEmpty()) {
			throw new EmptyElementException("message.input.event");
		}
		if(!eventForm.isHasAvatar()) {
			throw new InvalidImageException("message.input.required");
		}
		if(eventRepository.findIdByIdentify(eventForm.getCompanyId(), eventForm.getIdentify()).isPresent()) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(!StringUtils.isEmpty(eventForm.getKeysword()) && eventForm.getKeysword().split(",").length > maxKeysword) {
			throw new MaxKeyswordException("message.error.maxkeywords");
		}
		if(!StringUtils.isEmpty(eventForm.getUrlExtern()) && eventRepository.existsByUrlExtern(eventForm.getUrlExtern())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		final Photo photo = photoService.addPhoto(eventForm.getFile(), eventForm.getCompanyId(), PhotoType.event);
		final Event event = postEvent(new Event(eventForm.getCompanyId()), eventForm, userId);
		postEventDetail(new EventDetail(event), eventForm, photo.getId());
		createEventsCalendar(event.getId(), eventForm);
		return event;
	}
	
	@Transactional
	private final void clearEventsCalendar(final List<String> lines) {
		eventCalendarRepository.deleteLinesEventCalendar(ParseUtil.parseLinesUUID(lines));
	}
	
	@Transactional
	private final EventCalendar updateEventCalendar(final UUID uuid, String eventDate, Integer clockOpen, Integer clockClose, Integer wilaya) {
		final EventCalendar eventCalendar = eventCalendarRepository.findById(uuid).get();
		eventCalendar.setEventDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(eventDate));
		eventCalendar.setClockOpen(clockOpen);
		eventCalendar.setClockClose(clockClose);
		eventCalendar.setWilaya(wilaya);
		return eventCalendarRepository.save(eventCalendar);
	}
	
	@Transactional
	private final void updateEventsCalendar(final UUID eventId, final EventForm eventForm) {
		final List<EventCalendar> eventsCalendar = new ArrayList<EventCalendar>();
		for(int i = 0; i < eventForm.getEventsDate().size(); i++) {
			if(eventForm.getIdents().get(i).equals("-1")) {
				if(eventForm.getUpdated().get(i).equals("-1")) {
					final EventCalendar eventCalendar = new EventCalendar();
					eventCalendar.setEventUUID(eventId);
					eventCalendar.setEventDate(DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(eventForm.getEventsDate().get(i)));
					eventCalendar.setClockOpen(eventForm.getClocksOpen().get(i));
					eventCalendar.setClockClose(eventForm.getClocksClose().get(i));
					eventCalendar.setWilaya(eventForm.getWilayas().get(i));
					eventsCalendar.add(eventCalendar);
				} else {
					updateEventCalendar(UUID.fromString(eventForm.getUpdated().get(i)), eventForm.getEventsDate().get(i), 
							eventForm.getClocksOpen().get(i), eventForm.getClocksClose().get(i), eventForm.getWilayas().get(i));
				}
			}
		}
		if(!eventsCalendar.isEmpty()) {
			eventCalendarRepository.saveAll(eventsCalendar);
		}
	}
	
	@Override
	@Transactional
	public Event updateEvent(final EventForm eventForm, final int maxKeysword, final Long userId) {
		if(eventForm.getEventsDate().isEmpty()) {
			throw new EmptyElementException("message.input.event");
		}
		if(!eventForm.isHasAvatar()) {
			throw new InvalidImageException("message.input.required");
		}
		final Optional<Event> uOptional = eventRepository.findById(UUID.fromString(eventForm.getId()));
		if(uOptional.isPresent() && eventForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Event event = uOptional.get();
			if(!eventForm.getIdentify().equalsIgnoreCase(event.getIdentify()) 
					&& eventRepository.findIdByIdentify(eventForm.getCompanyId(), eventForm.getIdentify()).isPresent()) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(!StringUtils.isEmpty(eventForm.getKeysword()) && eventForm.getKeysword().split(",").length > maxKeysword) {
				throw new MaxKeyswordException("message.error.maxkeywords");
			}
			if(!StringUtils.isEmpty(eventForm.getUrlExtern()) && !eventForm.getUrlExtern().equalsIgnoreCase(event.getUrlExtern()) 
					&& eventRepository.existsByUrlExtern(eventForm.getUrlExtern())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			final EventDetail eventDetail = eventDetailRepository.findById(event.getId()).get();
			if(eventForm.isHasFileChanged()) {
				photoService.updatePhoto(eventDetail.getPhotoUUID(), eventForm.getFile());
			}
			postEvent(event, eventForm, userId);
			postEventDetail(eventDetail, eventForm, eventDetail.getPhotoUUID());
			if(eventForm.isUpdateCalendar()) {
				if(!eventForm.getTrashed().isEmpty()) {
					clearEventsCalendar(eventForm.getTrashed());
				}
				updateEventsCalendar(event.getId(), eventForm);
			}
			return event;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findEventsList(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = eventRepository.countAllEventCriteria(companyId, filter, search);
		final List<EventLine> lines = countResult == 0L ? new ArrayList<EventLine>() 
				: eventRepository.findAllEventCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutorEvent(final Long companyId) {
		return eventRepository.findAllAutorCriteria(companyId);
	}
	
	@Override
	@Transactional
	public Event deleteEvent(final String id, final Long companyId) {
		try {
			final Optional<Event> uOptional = eventRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Event event = uOptional.get();
			final UUID eventId = event.getId();
			final UUID photoId = eventDetailRepository.findPhotoUUIDById(eventId).get();
			final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentId(eventId, DocumentType.event);
			final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentId(eventId, DocumentType.event);
			eventDetailRepository.deleteById(eventId);
			eventCalendarRepository.deleteByEventUUID(eventId);
			eventRepository.delete(event);
			guestDocumentRepository.deleteByDocumentIdAndType(eventId, DocumentType.event);
			favoriteDocumentRepository.deleteByDocumentIdAndType(eventId, DocumentType.event);
			campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
			campaignRepository.deleteByDocumentIdAndType(eventId, DocumentType.event);
			filereaderService.deleteAllFilereaders(linesFiles);
			photoService.deletePhoto(photoId);
			return event;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional
	private final void removeEvents(final List<UUID> linesUUID) {
		final List<UUID> linesPhoto = eventDetailRepository.findAllPhotoUUIDById(linesUUID);
		final List<UUID> linesFiles = guestDocumentRepository.findAllFileUUIDByDocumentIds(linesUUID, DocumentType.event);
		final List<UUID> linesCampaigns = campaignRepository.findAllIdByDocumentIds(linesUUID, DocumentType.post);
		eventDetailRepository.deleteEventsDetail(linesUUID);
		eventCalendarRepository.deleteEventCalendarByEventIds(linesUUID);
		eventRepository.deleteEvents(linesUUID);
		guestDocumentRepository.deleteGuestDocumentByDocumentsIds(linesUUID, DocumentType.event);
		favoriteDocumentRepository.deleteFavoriteDocumentByDocumentsIds(linesUUID, DocumentType.event);
		campaignTargetRepository.deleteCampaignsTarget(linesCampaigns);
		campaignRepository.deleteCampaignsByDocumentsIds(linesUUID, DocumentType.event);
		filereaderService.deleteAllFilereaders(linesFiles);
		photoService.deleteAllPhotos(linesPhoto);
	}
	
	@Override
	@Transactional
	public void deleteEvents(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) eventRepository.countEvents(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			removeEvents(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllEvents(final Long companyId) {
		final List<UUID> linesUUID = eventRepository.findAllIdByCompanyId(companyId);
		if(!linesUUID.isEmpty()) {
			removeEvents(linesUUID);
		}
	}
	
}
