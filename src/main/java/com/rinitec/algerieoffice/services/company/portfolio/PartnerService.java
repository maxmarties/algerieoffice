package com.rinitec.algerieoffice.services.company.portfolio;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.PartnerRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.WorkRepository;
import com.rinitec.algerieoffice.persistence.dao.companymaps.GuestPartnerRepository;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.portfolio.PartneruserForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.company.portfolio.PartnerLine;

@Service
public class PartnerService implements IPartnerService {

	private PartnerRepository partnerRepository;
	private GuestPartnerRepository guestPartnerRepository;
	private WorkRepository workRepository;
	private IPhotoService photoService;
	
	@Autowired
	public PartnerService(PartnerRepository partnerRepository, GuestPartnerRepository guestPartnerRepository, 
			WorkRepository workRepository, IPhotoService photoService) {
		this.partnerRepository = partnerRepository;
		this.guestPartnerRepository = guestPartnerRepository;
		this.workRepository = workRepository;
		this.photoService = photoService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countGuestPartner(final Long companyId) {
		return guestPartnerRepository.countByCompanyId(companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countPartnerAndGuest(final Long companyId) {
		return partnerRepository.countByCompanyId(companyId) + guestPartnerRepository.countByCompanyId(companyId);
	}
	
	private final PartneruserForm parsePartneruserForm(final Partner partner) {
		final PartneruserForm partneruserForm = new PartneruserForm();
		partneruserForm.setId(partner.getId().toString());
		partneruserForm.setCompanyId(partner.getCompanyId());
		partneruserForm.setName(partner.getName());
		partneruserForm.setBiography(partner.getBiography());
		partneruserForm.setUrl(partner.getUrl());
		partneruserForm.setHasPingled(partner.getHasPingled());
		partneruserForm.setHasAvatar(partner.getPhotoUUID() != null);
		partneruserForm.setUrlAvatar(partner.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(partner.getPhotoUUID().toString()) 
				: "/static/picts/avatars/company-min.jpg");
		return partneruserForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PartneruserForm readPartneruserForm(final String id, final Long companyId) {
		try {
			final Optional<Partner> uOptional = partnerRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent() && companyId.equals(uOptional.get().getCompanyId())) {
				final Partner partner = uOptional.get();
				return parsePartneruserForm(partner);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Partner postPartner(final Partner partner, final PartneruserForm partneruserForm, final UUID photoUUID, final Long userId) {
		partner.setName(partneruserForm.getName());
		partner.setBiography(partneruserForm.getBiography());
		partner.setUrl(partneruserForm.getUrl());
		partner.setPhotoUUID(photoUUID);
		partner.setModifiedDate(new DateTime(Date.from(Instant.now())));
		partner.setAutorId(userId);
		partner.setHasPingled(partneruserForm.getHasPingled());
		return partnerRepository.save(partner);
	}
	
	@Override
	@Transactional
	public Partner addPartner(final PartneruserForm partneruserForm, final Long userId) {
		UUID photoUUID = null;
		if(partneruserForm.isHasAvatar()) {
			final Photo photo = photoService.addPhoto(partneruserForm.getFile(), partneruserForm.getCompanyId(), PhotoType.partner);
			photoUUID = photo.getId();
		}
		return postPartner(new Partner(partneruserForm.getCompanyId()), partneruserForm, photoUUID, userId);
	}
	
	@Override
	@Transactional
	public Partner updatePartner(final PartneruserForm partneruserForm, final Long userId) {
		final Optional<Partner> uOptional = partnerRepository.findById(UUID.fromString(partneruserForm.getId()));
		if(uOptional.isPresent() && partneruserForm.getCompanyId().equals(uOptional.get().getCompanyId())) {
			final Partner partner = uOptional.get();
			UUID photoUUID = partner.getPhotoUUID();
			if(partneruserForm.isHasFileChanged()) {
				if(partneruserForm.isHasAvatar()) {
					if(photoUUID != null) {
						photoService.updatePhoto(photoUUID, partneruserForm.getFile());
					} else {
						final Photo photo = photoService.addPhoto(partneruserForm.getFile(), partneruserForm.getCompanyId(), PhotoType.partner);
						photoUUID = photo.getId();
					}
				} else if(photoUUID != null) {
					photoService.deletePhoto(photoUUID);
					photoUUID = null;
				}
			}
			return postPartner(partner, partneruserForm, photoUUID, userId);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findPartnersList(final Long companyId, final Long filter, final String search, final int sort, 
			final int rows, final int page, final boolean hasDesc) {
		final Long countResult = partnerRepository.countAllPartnerCriteria(companyId, filter, search);
		final List<PartnerLine> lines = countResult == 0L ? new ArrayList<PartnerLine>() 
				: partnerRepository.findAllPartnerCriteria(companyId, filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutorPartner(final Long companyId) {
		return partnerRepository.findAllAutorCriteria(companyId);
	}
	
	@Override
	@Transactional
	public Partner deletePartner(final String id, final Long companyId) {
		try {
			final Optional<Partner> uOptional = partnerRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Partner partner = uOptional.get();
			if(partner.getPhotoUUID() != null) {
				photoService.deletePhoto(partner.getPhotoUUID());
			}
			workRepository.trashPartnerUUID(partner.getId());
			partnerRepository.delete(partner);
			return partner;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deletePartners(final List<String> lines, final Long companyId) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) partnerRepository.countPartners(companyId, linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			final List<UUID> linesPhoto = partnerRepository.findAllPhotoUUIDById(linesUUID);
			partnerRepository.deleteParteners(linesUUID);
			workRepository.trashPartners(linesUUID);
			photoService.deleteAllPhotos(linesPhoto);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPartners(final Long companyId) {
		final List<UUID> linesPhoto = partnerRepository.findAllPhotoUUIDByCompanyId(companyId);
		partnerRepository.deleteByCompanyId(companyId);
		workRepository.trashAllPartners(companyId);
		photoService.deleteAllPhotos(linesPhoto);
	}
	
}
